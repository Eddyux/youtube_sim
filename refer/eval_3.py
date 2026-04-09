import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
TARGET_ITEM_ID = "all-build-itx-too-late"


def validate_task_three(result=None, device_id=None, backup_dir=None):
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

    has_search = False
    has_like = False

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        if event.get("action") == "search_query" and extra_data.get("query") == "itx":
            has_search = True
        if event.get("action") == "toggle_video_like" and extra_data.get("item_id") == TARGET_ITEM_ID and extra_data.get("enabled") == "true":
            has_like = True

    return has_search and has_like


if __name__ == "__main__":
    print(validate_task_three())
