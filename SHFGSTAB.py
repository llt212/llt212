from datetime import datetime
from playsound import playsound

# 输入
alarm_time = input("请输入闹钟时间, 示例: 09:50:00 am\n")
# 时
alarm_hour = alarm_time[0:2]
# 分
alarm_minute = alarm_time[3:5]
# 秒
alarm_seconds = alarm_time[6:8]
# 上午或下午
alarm_period = alarm_time[9:11].upper()
print("完成闹钟设置..")

while True:
    now = datetime.now()
    current_hour = now.strftime("%I")
    current_minute = now.strftime("%M")
    current_seconds = now.strftime("%S")
    current_period = now.strftime("%p")

    # 时间判断
    if alarm_period == current_period:
        if alarm_hour == current_hour:
            if alarm_minute == current_minute:
                if alarm_seconds == current_seconds:
                    print("起来啦!")
                    # 闹钟铃声
                    playsound('audio.mp3')
                    break
