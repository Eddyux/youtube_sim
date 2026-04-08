import json
import os
import subprocess


def validate_task_five(result=None, device_id=None, backup_dir=None):
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

    if not (state.get('toggle_states') or {}).get('subscriptions'):
        return False

    for event in reversed(state.get('events', [])):
        details = event.get('details') or {}
        if event.get('action') == 'toggle_setting' and event.get('target') == 'subscriptions' and details.get('enabled') == 'true':
            return True
    return False


if __name__ == '__main__':
    print(validate_task_five())
