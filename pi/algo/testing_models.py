import subprocess

commandCd = "cd darknet-nnpack "
test_path = "testing/"
test_imgs = ["person2.jpg","person.jpg", "notperson.jpg"]
weights = "yolov3-tiny.weights"
model = "cfg/yolov3-tiny.cfg"
action = "detect"
prog = "./darknet"

command = prog+" "+action+" "+model+" "+weights
total_command = commandCd +"\n" + command

with open('outfileOr.out', 'w') as out:
    p = subprocess.Popen(total_command, stdout=out, stdin=subprocess.PIPE, shell=True)
    
    for img in test_imgs:
        p.stdin.write((test_path+img+"\n").encode())
        p.stdin.flush()
    
    p.stdin.write(("^c").encode())
    p.stdin.flush()
