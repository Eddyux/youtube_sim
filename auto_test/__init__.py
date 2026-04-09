from ..base import AppTasks, TaskItem

from .eval_1 import validate_task_one
from .eval_2 import validate_task_two
from .eval_3 import validate_task_three
from .eval_4 import validate_task_four
from .eval_5 import validate_task_five
from .eval_6 import validate_task_six
from .eval_7 import validate_task_seven
from .eval_8 import validate_task_eight
from .eval_9 import validate_task_nine
from .eval_10 import validate_task_ten
from .eval_11 import validate_task_eleven
from .eval_12 import validate_task_twelve
from .eval_13 import validate_task_thirteen
from .eval_14 import validate_task_fourteen
from .eval_15 import validate_task_fifteen
from .eval_16 import validate_task_sixteen
from .eval_17 import validate_task_seventeen
from .eval_18 import validate_task_eighteen
from .eval_19 import validate_task_nineteen
from .eval_20 import validate_task_twenty
from .eval_21 import validate_task_twenty_one
from .eval_22 import validate_task_twenty_two
from .eval_23 import validate_task_twenty_three
from .eval_24 import validate_task_twenty_four
from .eval_25 import validate_task_twenty_five
from .eval_26 import validate_task_twenty_six
from .eval_27 import validate_task_twenty_seven
from .eval_28 import validate_task_twenty_eight
from .eval_29 import validate_task_twenty_nine
from .eval_30 import validate_task_thirty


YOUTUBE_TASKS = AppTasks(
    package_name="com.example.youtube_sim",
    task_items=[
        TaskItem(
            instruction="查看我的观看历史",
            verify_func=validate_task_one,
            human_steps=3,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="搜索apple,给第一个视频点赞加收藏",
            verify_func=validate_task_two,
            human_steps=6,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="我想看itx装机视频，搜索itx并给第一个视频点赞",
            verify_func=validate_task_three,
            human_steps=5,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="查看我喜欢的视频，然后播放其中华语歌手的歌",
            verify_func=validate_task_four,
            human_steps=5,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="打开subscriptions按钮",
            verify_func=validate_task_five,
            human_steps=4,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="删除稍后观看的最后一个视频",
            verify_func=validate_task_six,
            human_steps=4,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="给Jay Chou点关注",
            verify_func=validate_task_seven,
            human_steps=4,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="给周杰伦的青花瓷评论“这首歌太动听了”",
            verify_func=validate_task_eight,
            human_steps=6,
            is_reasoning=False,
        ),
        TaskItem(
            instruction="在历史记录页面查看我一共看了几条周杰伦的mv",
            verify_func=validate_task_nine,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="查看我喜欢的第一条视频的时长是多少",
            verify_func=validate_task_ten,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="查看restricted model的状态是打开还是关闭",
            verify_func=validate_task_eleven,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="查看mentions按钮的状态是打开还是关闭",
            verify_func=validate_task_twelve,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="查看当前App language选项是什么",
            verify_func=validate_task_thirteen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="首页apple视频页部分有几个视频是关于手机的",
            verify_func=validate_task_fourteen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="搜索iphone，查看本次搜索有几个结果",
            verify_func=validate_task_fifteen,
            human_steps=4,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="查看首页第一个视频播放页下方的相关视频有多少个",
            verify_func=validate_task_sixteen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="首页第一个视频的评论区的总点赞数是多少",
            verify_func=validate_task_seventeen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下我稍后播放里的视频总时长加起来是多久",
            verify_func=validate_task_eighteen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下喜欢里的视频总时长加起来是多少",
            verify_func=validate_task_nineteen,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下周杰伦的粉丝数是多少",
            verify_func=validate_task_twenty,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="刷一下短视频，刷到的前四个短视频有几个是关于电脑的？",
            verify_func=validate_task_twenty_one,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="刷一下短视频，刷到的前四个短视频有几个是关于mini电脑的？",
            verify_func=validate_task_twenty_two,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="刷一下短视频，刷到的前四个短视频关于电脑的有多少点赞？",
            verify_func=validate_task_twenty_three,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="刷一下短视频，刷到的前四个短视频中，电脑相关的短视频时长一共多少秒，回答多少秒即可？",
            verify_func=validate_task_twenty_four,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="周杰伦夜曲的MV中，最新的评论的点赞数是多少？",
            verify_func=validate_task_twenty_five,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="history里的播放记录有几条？",
            verify_func=validate_task_twenty_six,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下Video quality on mobile networks的选项是什么？",
            verify_func=validate_task_twenty_seven,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="看一下Video quality on Wi-Fi的选项是什么？",
            verify_func=validate_task_twenty_eight,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="首页all下面前八个视频中有几个是关于电脑的？",
            verify_func=validate_task_twenty_nine,
            human_steps=3,
            is_reasoning=True,
        ),
        TaskItem(
            instruction="当前app版本号多少？",
            verify_func=validate_task_thirty,
            human_steps=3,
            is_reasoning=True,
        ),
    ],
)
