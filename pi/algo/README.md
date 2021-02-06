
# Setup environment 
- drive, colab, raspberry pi, pc (optional)

## Install darknet-NNPACK raspberry pi
[HaroldSP/Harold](https://github.com/HaroldSP/Harold/wiki/4.-Installing-darknet-nnpack-to-run-YOLOv3-on-Raspberry-pi-4)

## Install darknet (linux / win)
[AlexeyAB/darknet](https://github.com/AlexeyAB/darknet)

## train on google colab
[read](https://medium.com/@quangnhatnguyenle/how-to-train-yolov3-on-google-colab-to-detect-custom-objects-e-g-gun-detection-d3a1ee43eda1)
# test
## test model performance in raspberry pi 4b (in seconds)
 testing_models.py
 
# Automate things

# prepare data
[ultralytics/JSON2YOLO](https://github.com/ultralytics/JSON2YOLO/blob/master/general_json2yolo.py)
[coco api](https://stackoverflow.com/a/62770484/13771772)

```
weight_3000 600px

Enter Image Path: testing/person2.jpg: Predicted in 2.930156 seconds.
person: 97%
person: 96%
person: 88%
Enter Image Path: testing/person.jpg: Predicted in 2.186202 seconds.
person: 80%
Enter Image Path: testing/notperson.jpg: Predicted in 2.161005 seconds. 
```
```
weight_3000 400px
Enter Image Path: testing/person2min.jpg: Predicted in 2.864548 seconds.
person: 96%
person: 95%
person: 82%
Enter Image Path: testing/personmin.jpg: Predicted in 2.158635 seconds.
person: 83%
Enter Image Path: testing/notpersonmin.jpg: Predicted in 2.103251 seconds.
person: 53% 
```
```
original
Enter Image Path: testing/person2.jpg: Predicted in 2.995017 seconds.
person: 98%
person: 96%
person: 68%
Enter Image Path: testing/person.jpg: Predicted in 2.234735 seconds.
horse: 94%
dog: 91%
dog: 90%
person: 86%
Enter Image Path: testing/notperson.jpg: Predicted in 2.184598 seconds.
dog: 91%
dog: 85%
```
