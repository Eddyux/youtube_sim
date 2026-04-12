import json
import os
import subprocess


PACKAGE_NAME = "com.example.youtube_sim"
DEVICE_FILE_PATH = "files/messages.json"
STATE_FILE_PATH = "files/task_state.json"
TARGETS = {
    "music-jay-chou-nocturne": "我是周杰伦十年老粉",
    "music-blue-porcelain": "这首歌让我想起了那个女孩",
}


def _read_device_json(device_path, local_name, device_id=None, backup_dir=None):
    local_path = os.path.join(backup_dir, local_name) if backup_dir else local_name
    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", PACKAGE_NAME, "cat", device_path])
    subprocess.run(cmd, stdout=open(local_path, "w", encoding="utf-8"), stderr=subprocess.DEVNULL, check=False)
    with open(local_path, "r", encoding="utf-8") as f:
        return json.load(f)


def validate_task_thirty_four(result=None, device_id=None, backup_dir=None):
    try:
        message_data = _read_device_json(DEVICE_FILE_PATH, "messages.json", device_id, backup_dir)
        state = _read_device_json(STATE_FILE_PATH, "task_state.json", device_id, backup_dir)
        events = message_data if isinstance(message_data, list) else [message_data]
    except Exception:
        return False

    liked = {item_id: False for item_id in TARGETS}
    saved = {item_id: False for item_id in TARGETS}
    commented = {item_id: False for item_id in TARGETS}

    for event in reversed(events):
        extra_data = event.get("extra_data", {})
        item_id = extra_data.get("item_id")
        if item_id not in TARGETS:
            continue
        if event.get("action") == "toggle_video_like" and extra_data.get("enabled") == "true":
            liked[item_id] = True
        if event.get("action") == "toggle_video_save" and extra_data.get("enabled") == "true":
            saved[item_id] = True
        if event.get("action") == "submit_comment" and extra_data.get("text") == TARGETS[item_id]:
            commented[item_id] = True

    liked_video_ids = set(state.get("liked_video_ids", []))
    saved_video_ids = set(state.get("saved_video_ids", []))
    return all(
        liked[item_id]
        and saved[item_id]
        and commented[item_id]
        and item_id in liked_video_ids
        and item_id in saved_video_ids
        for item_id in TARGETS
    )


if __name__ == "__main__":
    print(validate_task_thirty_four())
