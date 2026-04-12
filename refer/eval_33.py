import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
STATE_FILE_PATH = "files/task_state.json"
TARGET_ITEM_ID = "music-blue-porcelain"
TARGET_TEXT = "这个MV真清晰"
QUALITY_PAGES = {"quality", "quality_settings"}
QUALITY_GROUP_KEYS = {"quality_mobile", "quality_wifi"}


def _read_device_json(device_path, local_name, device_id=None, backup_dir=None):
    local_path = os.path.join(backup_dir, local_name) if backup_dir else local_name
    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", device_path])
    subprocess.run(cmd, stdout=open(local_path, "w", encoding="utf-8"), stderr=subprocess.DEVNULL, check=False)
    with open(local_path, "r", encoding="utf-8") as f:
        return json.load(f)


def validate_task_thirty_three(result=None, device_id=None, backup_dir=None):
    try:
        message_data = _read_device_json(DEVICE_FILE_PATH, "messages.json", device_id, backup_dir)
        state = _read_device_json(STATE_FILE_PATH, "task_state.json", device_id, backup_dir)
        events = message_data if isinstance(message_data, list) else [message_data]
    except Exception:
        return False

    enabled_quality = False
    posted_comment = False

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        if event.get("action") == "select_option" and event.get("page") in QUALITY_PAGES:
            if (
                extra_data.get("group_key") in QUALITY_GROUP_KEYS
                and extra_data.get("option_key") == "higher_picture_quality"
            ):
                enabled_quality = True
        if event.get("action") == "submit_comment" and event.get("page") == "comments_sheet":
            if extra_data.get("item_id") == TARGET_ITEM_ID and extra_data.get("text") == TARGET_TEXT:
                posted_comment = True

    selected_options = state.get("selected_options", {})
    quality_persisted = any(
        selected_options.get(group_key) == "higher_picture_quality"
        for group_key in QUALITY_GROUP_KEYS
    )
    return enabled_quality and quality_persisted and posted_comment


if __name__ == "__main__":
    print(validate_task_thirty_three())
