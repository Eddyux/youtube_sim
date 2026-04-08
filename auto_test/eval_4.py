import json
import os
import subprocess


JAY_CHOU_IDS = {'music-jay-chou-i-do', 'all-jay-chou-i-do'}


def validate_task_four(result=None, device_id=None, backup_dir=None):
    state_path = os.path.join(backup_dir, 'task_state.json') if backup_dir else 'task_state.json'
    cmd = ['adb']
    if device_id:
        cmd.extend(['-s', device_id])
    cmd.extend(['exec-out', 'run-as', 'com.example.youtube_sim', 'cat', 'files/task_state.json'])
    subprocess.run(cmd, stdout=open(state_path, 'w', encoding='utf-8'), stderr=subprocess.DEVNULL, check=False)

    try:
        state = json.load(open(state_path, 'r', encoding='utf-8'))
    except Exception:
        return False

    opened_liked = state.get('active_overlay') == 'playlist:liked_videos'
    if not opened_liked:
        for event in reversed(state.get('events', [])):
            if event.get('action') == 'open_overlay' and event.get('target') == 'playlist:liked_videos':
                opened_liked = True
                break

    if state.get('last_played_video_id') in JAY_CHOU_IDS:
        return opened_liked

    for event in reversed(state.get('events', [])):
        if event.get('action') == 'play_video' and event.get('target') in JAY_CHOU_IDS:
            return opened_liked
    return False


if __name__ == '__main__':
    print(validate_task_four())
