import json
import os
import subprocess


TARGET_TEXT = '这首歌太动听了'


def validate_task_eight(result=None, device_id=None, backup_dir=None):
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

    comments = ((state.get('posted_comments') or {}).get('music-blue-porcelain') or [])
    return TARGET_TEXT in comments


if __name__ == '__main__':
    print(validate_task_eight())
