import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
JAY_CHOU_IDS = {"music-jay-chou-i-do", "all-jay-chou-i-do"}


def validate_task_four(result=None, device_id=None, backup_dir=None):
    message_file_path = os.path.join(backup_dir, "messages.json") if backup_dir else "messages.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", DEVICE_FILE_PATH])
    subprocess.run(cmd, stdout=open(message_file_path, "w", encoding="utf-8"), stderr=subprocess.DEVNULL, check=False)

    try:
        with open(message_file_path, "r", encoding="utf-8") as f:
            data = json.load(f)
            events = data if isinstance(data, list) else [data]
    except Exception:
        return False

    opened_liked_videos = False
    played_jay_chou = False

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        if event.get("action") == "open_page" and event.get("page") == "playlist:liked_videos":
            opened_liked_videos = True
        if event.get("action") == "play_video" and event.get("page") == "playlist:liked_videos" and extra_data.get("item_id") in JAY_CHOU_IDS:
            played_jay_chou = True

    return opened_liked_videos and played_jay_chou


if __name__ == "__main__":
    print(validate_task_four())
