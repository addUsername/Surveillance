from VideoStream import Vs
from Detection import Det
from Output import Out
from OutputAndSave import Oas
from base64 import b64encode
from picamera import PiCamera
import requests
import MyUtils
import time
import stomper
import websocket
import threading


settings_path = "config.properties"

def pingAndDownloadSettings(url):
    print("downloading")
    print(url)
    flag = True
    time = 1
    while(flag):
        x = requests.get(url)
        print(x)
        if(x.status_code < 300):            
            flag = False
        else:
            time.sleep(time)
            time = time * 2
            if(time > 1500):
                exit(1)    
    MyUtils.persistSettings(settings_path, x.text)
    
def startCamera():
    print("starting camera")
    photoCamera = PiCamera()
    photoCamera.resolution = (256,256)
    photoCamera.rotation = settings["rotation"]
    return photoCamera

# WEBSOCKET
def on_message(ws, message):

    print('### message ###')
    print('<< ' + message)
    
    message = message.split("\n\n")[1]
    if("OFF" in message):
        system.exit(0)
        return
    
    if("STATUS" in message):
        pass
    
    if("MINI" in message):
        lock.acquire()
        ws.send(stomper.send("/app/string/"+settings["id"], "STATUS\n\nRUNNING"))
        photoCamera.close()
        vs.startRecordingMini(Out(ws,sendPath))
        ws.send(stomper.send("/app/string/"+settings["id"], "STATUS\n\nUP"))
        lock.release()
        return
        
    if("STREAM" in message):
        lock.acquire()
        ws.send(stomper.send("/app/string/"+settings["id"], "STATUS\n\nRUNNING"))
        photoCamera.close()
        output = ""    
        # Persist video?
        if(settings["saveVideo"]):
            videos_path = settings["videosPath"]+str(int(time.time()))+"."+settings["videoExt"]
            output = Oas(ws,sendPath,videos_path)
        else:    
            output = Out(ws,sendPath)
            
        vs.startRecording(output)
        # change status to off
        ws.send(stomper.send("/app/string/"+settings["id"], "STATUS\n\nUP"))
        lock.release()
        return
    
def on_error(ws, error):
    print('### error ###')
    print(error)

def on_close(ws):
    print('### closed ###')

def on_open(ws):
    print('### opened ###')
    ws.send(CONNECT)
    ws.send(stomper.subscribe(settings["subscribePath"]+settings["id"]+"/",settings["id"],ack="auto"))
    ws.send(stomper.send("/app/string/"+settings["id"], "STATUS\n\nUP"))

        
def pushUser(url, imgpath):
    print("push header")
    headers = {'Content-type': 'image/jpeg'}
    data = {"file" : open(imgpath,'rb')}
    requests.post(url, headers=headers, data=data)
    

if __name__== "__main__":
    
    websocket.enableTrace(True)
    # this is gonna be used when running a streaming,so th while(true) stops till
    lock = threading.Lock()
    # Descargar config.properties
    settings = MyUtils.loadSettings(settings_path)
    x = pingAndDownloadSettings("http://"+settings["host"]+settings["iniPath"]+settings["id"])
    settings = MyUtils.loadSettings(settings_path)
    
    # Iniciar detection y connection
    print("connect")
    CONNECT = "CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n"
    connectionUrl = "ws://"+settings["host"]+"/stream"
    sendPath = settings["sendPath"]+settings["id"]
    pushUserPath = "http://"+settings["host"]+settings["pushPath"]+settings["id"]
    print(connectionUrl)
    print(sendPath)
    print(pushUserPath)
    
    ws = websocket.WebSocketApp( connectionUrl,
                            on_open = on_open,
                            on_message = on_message,
                            on_error = on_error,
                            on_close = on_close)
    
    wst = threading.Thread(target=ws.run_forever)
    wst.daemon = True
    wst.start()
    
    # Load videostream
    vs = Vs(settings)
    # Start detecting
    d = Det(settings)
    time.sleep(4)
    photoCamera = startCamera()
    img = "test.jpg"    
    while(True):
        print("capture()")
        try:
            photoCamera.capture(img)
        except:
            lock.acquire()
            lock.release()
            photoCamera = startCamera()
            photoCamera.capture(img)
        if(d.detect(img)):
        #time.sleep(1)
        #if(False):
            # send request to inform android client
            photoCamera.close()
            pushUser(pushUserPath,img)
            print("lock")
            lock.acquire()
            lock.release()
            photoCamera= startCamera()