import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
STATE_FILE_PATH = "files/task_state.json"
TARGET_ITEM_IDS = {"all-taylor-ophelia", "music-taylor-ophelia"}
TARGET_TEXT = "Although your song sounds great, I still prefer listening to Jay Chou"


def _read_device_json(device_path, local_name, device_id=None, backup_dir=None):
    local_path = os.path.join(backup_dir, local_name) if backup_dir else local_name
    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", device_path])
    subprocess.run(cmd, stdout=open(local_path, "w", encoding="utf-8"), stderr=subprocess.DEVNULL, check=False)
    with open(local_path, "r", encoding="utf-8") as f:
        return json.load(f)


def validate_task_thirty_five(result=None, device_id=None, backup_dir=None):
    try:
        message_data = _read_device_json(DEVICE_FILE_PATH, "messages.json", device_id, backup_dir)
        state = _read_device_json(STATE_FILE_PATH, "task_state.json", device_id, backup_dir)
        events = message_data if isinstance(message_data, list) else [message_data]
    except Exception:
        return False

    liked_targets = set()
    saved_targets = set()
    commented_targets = set()

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        item_id = extra_data.get("item_id")
        if item_id not in TARGET_ITEM_IDS:
            continue
        if event.get("action") == "toggle_video_like" and extra_data.get("enabled") == "true":
            liked_targets.add(item_id)
        if event.get("action") == "toggle_video_save" and extra_data.get("enabled") == "true":
            saved_targets.add(item_id)
        if event.get("action") == "submit_comment" and extra_data.get("text") == TARGET_TEXT:
            commented_targets.add(item_id)

    liked_video_ids = set(state.get("liked_video_ids", []))
    saved_video_ids = set(state.get("saved_video_ids", []))
    for item_id in TARGET_ITEM_IDS:
        if item_id in liked_targets and item_id in saved_targets and item_id in commented_targets:
            if item_id in liked_video_ids and item_id in saved_video_ids:
                return True
    return False


if __name__ == "__main__":
    print(validate_task_thirty_five())
