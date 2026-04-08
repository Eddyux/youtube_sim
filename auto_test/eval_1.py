import json
import os
import subprocess


def validate_task_one(result=None, device_id=None, backup_dir=None):
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

    if state.get('active_overlay') == 'history':
        return True

    for event in reversed(state.get('events', [])):
        if event.get('action') == 'open_overlay' and event.get('target') == 'history':
            return True
    return False


if __name__ == '__main__':
    print(validate_task_one())
