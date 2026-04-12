import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
STATE_FILE_PATH = "files/task_state.json"
TARGET_ITEM_ID = "music-blue-porcelain"
TARGET_PAGE = f"video_play:{TARGET_ITEM_ID}"


def _read_device_json(device_path, local_name, device_id=None, backup_dir=None):
    local_path = os.path.join(backup_dir, local_name) if backup_dir else local_name
    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", device_path])
    subprocess.run(cmd, stdout=open(local_path, "w", encoding="utf-8"), stderr=subprocess.DEVNULL, check=False)
    with open(local_path, "r", encoding="utf-8") as f:
        return json.load(f)


def validate_task_thirty_two(result=None, device_id=None, backup_dir=None):
    try:
        message_data = _read_device_json(DEVICE_FILE_PATH, "messages.json", device_id, backup_dir)
        state = _read_device_json(STATE_FILE_PATH, "task_state.json", device_id, backup_dir)
        events = message_data if isinstance(message_data, list) else [message_data]
    except Exception:
        return False

    played_target = False
    selected_quality = False
    disabled_ambient_mode = False

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        if event.get("action") == "play_video" and extra_data.get("item_id") == TARGET_ITEM_ID:
            played_target = True
        if event.get("action") == "select_option" and event.get("page") == TARGET_PAGE:
            if (
                extra_data.get("group_key") == "quality_current_video"
                and extra_data.get("option_key") == "higher_picture_quality"
                and extra_data.get("item_id") == TARGET_ITEM_ID
            ):
                selected_quality = True
        if event.get("action") == "toggle_setting" and event.get("page") == TARGET_PAGE:
            if (
                extra_data.get("item_id") == TARGET_ITEM_ID
                and extra_data.get("key") == "ambient_mode"
                and extra_data.get("enabled") == "false"
            ):
                disabled_ambient_mode = True

    toggle_states = state.get("toggle_states", {})
    selected_options = state.get("selected_options", {})
    return (
        played_target
        and selected_quality
        and disabled_ambient_mode
        and selected_options.get("quality_current_video") == "higher_picture_quality"
        and toggle_states.get("ambient_mode") is False
    )


if __name__ == "__main__":
    print(validate_task_thirty_two())
