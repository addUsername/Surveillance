from picamera import PiCamera
from websocket import create_connection
import stomper
import io
import requests
import time
from base64 import b64encode
'''
pillar url bookmark/surv/record and stream video from camera simultaneusly
This class allows to do whatever we want on camera.start_recording(), it will
send video frames to socket and storing video on pi simultaneusly

stream video travels by websocket->encode b64->decode utf-8.
bad things..
MJPEG is easy to stream an <img/> tag if enought but its size is too much fukin big
sending binary data as string sucks too (+30% size)

TODO
send data as binary aka no sT(ext)omp/websockets and try with normal sockets or Rsockets even request multipart/x-mixed-replace

'''
class MyOutput:
    
    host = "192.168.1.51:8080"
    
    def __init__(self):
        self.output_file = io.open("video.mjpg","wb")
        self.connect()
        time.sleep(1) 

    def connect(self):
        
        self.ws = create_connection("ws://"+self.host+"/mjpeg", timeout=5)
        self.ws.send("CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n")
        sub = stomper.subscribe("/topic/info","1",ack="auto")
        self.ws.send(sub)
        print(self.ws.recv())
        
        
    def write(self,buf):
        self.output_file.write(buf)
        # add id here?
        message = stomper.send("/app/mjpeg",b64encode(buf).decode("utf-8"))
        #print(type(b64encode(buf)))
        #print(type(b64encode(buf).decode("utf-8")))
        try:
            #self.ws.send(buf,websocket.ABNF.OPCODE_BINARY)
            #self.ws.send_binary(buf)
            self.ws.send(message)
            # print(self.ws.recv().split("\n\n")[1][:-1])
            self.ws.recv()
        except:
            self.connect()
            self.ws.send(message)
            print(self.ws.recv())
        print("send")
        
    def flush(self):
        self.output_file.flush()
        print("flush")
        
    def close(self):
        self.output_file.close()
        self.ws.close()
        print("close")
        
x = requests.get("http://192.168.1.51:8080/ini/1");
camera = PiCamera()
camera.resolution = (640,360)
camera.framerate = 30
camera.rotation = 180

output = MyOutput()
#camera.start_recording(output,format="mjpeg")
camera.start_recording(output,format="mjpeg", bitrate=10000000)
#camera.start_recording("video.mjpg")

camera.wait_recording(10)
camera.stop_recording()
output.close()
time.sleep(3)
x = requests.get("http://192.168.1.51:8080/save");
print(x)
