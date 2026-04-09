import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
TARGET_ITEM_ID = "all-screenshot-to-code"


def validate_task_six(result=None, device_id=None, backup_dir=None):
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

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        if event.get("action") != "remove_playlist_item":
            continue
        if event.get("page") != "playlist:watch_later":
            continue
        if extra_data.get("playlist_key") == "watch_later" and extra_data.get("item_id") == TARGET_ITEM_ID:
            return True
    return False


if __name__ == "__main__":
    print(validate_task_six())
