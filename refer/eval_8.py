import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
TARGET_TEXT = "\u8fd9\u9996\u6b4c\u592a\u52a8\u542c\u4e86"
TARGET_ITEM_ID = "music-blue-porcelain"


def validate_task_eight(result=None, device_id=None, backup_dir=None):
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
        if event.get("action") != "submit_comment":
            continue
        if event.get("page") != "comments_sheet":
            continue
        if extra_data.get("item_id") == TARGET_ITEM_ID and extra_data.get("text") == TARGET_TEXT:
            return True
    return False


if __name__ == "__main__":
    print(validate_task_eight())
