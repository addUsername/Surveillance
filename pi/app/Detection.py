import cv2
import numpy as np

class Det:
    
    def __init__(self, settings):
        self.s = settings
        self.net = cv2.dnn.readNetFromDarknet("/resources/det/yolov3-tiny.cfg", self.s["weights"])
        self.net.setPreferableBackend(cv2.dnn.DNN_BACKEND_DEFAULT)
        self.net.setPreferableTarget(cv2.dnn.DNN_TARGET_CPU)
        self.output_layer = [_layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()][0]
    
    def detect(self, path_img):
        frame = cv2.im_read(path_img)
        blob = cv2.dnn.blobFromImage(frame, 1.0/255, (256, 256), True, crop=False)
        self.net.setInput(blob)
        predictions = net.forward(self.output_layer)
        
        for i in range(predictions.shape[0]):
            prob_arr=predictions[i][5:]
            class_index=prob_arr.argmax(axis=0)
            confidence= prob_arr[class_index]
            print(confidence)
            if confidence > self.s["threshold"]:                
                return True        
        return False
        