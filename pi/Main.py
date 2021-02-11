import Connection
import Detection
import Threads

    host = "192.168.1.51:8080"
    http = "http://"
    
    def setupVideoCamera(self):
        
        self.camera = PiCamera()
        self.camera.resolution = (640,360)
        self.camera.framerate = 30
        self.camera.rotation = 180

    def startRecording(self):
        self.setupVideoCamera()
        self.camera.start_recording(self.con,format="mjpeg", bitrate=10000000)
        self.camera.wait_recording(10)
        self.camera.stop_recording()
        self.camera.close()
        return
        
    def setup(self):
        # esto sera una lista con toda la info ¿paths, etc..?
        await x = requests.get(http+host+"/ini/1");
        self.detect = Detection(0.14)
        self.con = Connection(self.host,"1")
        self.setupVideoCamera();
    
    if __name__== "__main__":
        setup()
        photoCamera = PiCamera()
        while(True):
            photoCamera.capture("test.jpg")
            if(self.detect("test.jpg")):
                # send request to inform push android client 
                photocamera.close()
                self.startRecording()
                photocamera = PiCamera()
                
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
