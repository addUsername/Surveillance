from picamera import PiCamera
class Vs:
    
    resDict={"LOW":(320,320),"MEDIUM":(640,640),"HIGH":(1080,768),"VERY_HIGH":(1920,1080)}
    
    def __init__(self, settings):
        self.s = settings
        
    def startRecording(self, output):
         
        camera = PiCamera()
        camera.resolution = self.resDict[self.s["videoRes"]]
        camera.framerate = int(self.s["framerate"])
        camera.rotation = int(self.s["rotation"])
        camera.start_recording(output,format=self.s["videoExt"].lower(), bitrate=int(self.s["bitrate"]))
        camera.wait_recording(int(self.s["timeRecording"]))
        camera.stop_recording()
        camera.close()
        print("finish recording")
        
    def startRecordingMini(self, output):
        camera = PiCamera()
        camera.resolution = self.resDict["LOW"]
        camera.framerate = int(self.s["framerate"])
        camera.rotation = int(self.s["rotation"])
        
        camera.start_recording(output,format="mjpeg", bitrate=100000)
        camera.wait_recording(int(self.s["timeRecording"]))
        camera.stop_recording()
        camera.close()
        print("finish mini recording")
    