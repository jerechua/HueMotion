import sys
from lib import Leap

class Listener(Leap.Listener):
    def on_connect(self, controller):
        print "Connected"

    def on_frame(self, controller):
        # print "Frame available"
        frame = controller.frame()

        print "Frame id: %d, timestamp: %d, hands: %d, fingers: %d" % (
          frame.id, frame.timestamp, len(frame.hands), len(frame.fingers))
        pass

def main():

    listener = Listener()
    controller = Leap.Controller()
    controller.add_listener(listener)

    # Keep this process running until Enter is pressed
    print "Press Enter to quit..."
    try:
        sys.stdin.readline()
    except KeyboardInterrupt:
        pass

if __name__ == "__main__":
    main()
