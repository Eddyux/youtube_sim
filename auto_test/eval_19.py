def validate_task_nineteen(result=None,device_id=None,backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False
    if 'final_message' in result and (
            '8:00' in result['final_message'] or
            '八分' in result['final_message']
    ):
        return True
    else:
        return False

if __name__ == '__main__':
    print(validate_task_nineteen())
