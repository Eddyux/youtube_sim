import json
import os
import subprocess


def validate_task_six(result=None, device_id=None, backup_dir=None):
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

    watch_later = ((state.get('playlist_items') or {}).get('watch_later') or [])
    if 'all-screenshot-to-code' in watch_later:
        return False

    for event in reversed(state.get('events', [])):
        details = event.get('details') or {}
        if event.get('action') == 'remove_playlist_item' and event.get('target') == 'all-screenshot-to-code' and details.get('playlist') == 'watch_later':
            return True
    return False


if __name__ == '__main__':
    print(validate_task_six())
