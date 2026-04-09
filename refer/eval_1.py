import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
ACTION_VALUE = "open_page"
PAGE_VALUE = "history"


def validate_task_one(result=None, device_id=None, backup_dir=None):
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
        if event.get("action") == ACTION_VALUE and event.get("page") == PAGE_VALUE:
            return True
    return False


if __name__ == "__main__":
    print(validate_task_one())
