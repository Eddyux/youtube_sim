def validate_task_ten(result=None,device_id=None,backup_dir=None):
    if result is None:
        return False
    final_message = result.get("final_message")
    if not isinstance(final_message, str):
        return False
    if 'final_message' in result and (
            '3:58' in result['final_message'] or
            '三分五十八秒' in result['final_message']
    ):
        return True
    else:
        return False

if __name__ == '__main__':
    print(validate_task_ten())
