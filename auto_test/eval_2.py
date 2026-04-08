import json
import os
import subprocess


def validate_task_two(result=None, device_id=None, backup_dir=None):
    state_path = os.path.join(backup_dir, 'task_state.json') if backup_dir else 'task_state.json'
    cmd = ['adb']
    if device_id:
        cmd.extend(['-s', device_id])
    cmd.extend(['exec-out', 'run-as', 'com.example.youtube_sim', 'cat', 'files/task_state.json'])
    subprocess.run(cmd, stdout=open(state_path, 'w', encoding='utf-8'), stderr=subprocess.DEVNULL, check=False)

    try:
        state = json.load(open(state_path, 'r', encoding='utf-8'))
        feed = json.load(open(os.path.join(os.path.dirname(__file__), '..', 'app', 'src', 'main', 'assets', 'data', 'home_feed.json'), 'r', encoding='utf-8'))
    except Exception:
        return False

    first_id = None
    for tab in feed.get('tabs', []):
        if tab.get('key') == 'live':
            continue
        for item in tab.get('items', []):
            if 'apple' in item.get('title', '').lower() or 'apple' in item.get('creator', '').lower():
                first_id = item.get('id')
                break
        if first_id:
            break

    if not first_id:
        return False

    searched = state.get('search_query', '').strip().lower() == 'apple'
    if not searched:
        for event in reversed(state.get('events', [])):
            if event.get('action') == 'search_query_changed' and (event.get('details') or {}).get('query') == 'apple':
                searched = True
                break

    return searched and first_id in (state.get('liked_video_ids') or []) and first_id in (state.get('saved_video_ids') or [])


if __name__ == '__main__':
    print(validate_task_two())
