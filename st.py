import time
import os

def focus_timer(minutes):
    seconds = minutes * 60
    while seconds > 0:
        print("倒计时还剩{}秒".format(seconds))
        time.sleep(1)
        seconds -= 1

    # 播放声音提醒
    os.system('say "时间到了！"')

if __name__ == "__main__":
    focus_timer(25)
