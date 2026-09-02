package com.dumboj.case1.chat;

import com.dumboj.case1.model.ModelProvider;
import com.dumboj.case1.model.OpenAiCompatibleChatModelFactory;
import com.dumboj.case1.model.ProviderSettings;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

import java.util.List;

/**
 * 普通带会话前后文的内容样例 {@link InMemoryChatMemoryStore}
 *
 * @author : Dumbo
 */
public class SimpleChatMemory {
    public static void main(String[] args) {
        ProviderSettings providerSettings = ProviderSettings.fromEnvironment();
        ChatModel chatModel = new OpenAiCompatibleChatModelFactory().create(ModelProvider.CUSTOM, providerSettings);

        //1. 创建内存消息(指定最大保存消息数量，底层内存 ConcurrentHashMap 保存)
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(6);

        //2. 创建系统 + 用户会话消息，并添加至 ChatMemory
        String sMsg = "你是旅行专家、知名旅游博主";
        SystemMessage sysMsg = SystemMessage.from(sMsg);
        String uMsg = "我想去北京玩两天";
        UserMessage usrMsg = UserMessage.from(uMsg);

        chatMemory.add(sysMsg,usrMsg);

        //3. 首次大语言模型提问
        ChatResponse firstResp = chatModel.chat(chatMemory.messages());
        System.out.printf("首次会话响应:\n %s ",firstResp.aiMessage().text());

        //4. 保存首次会话响应内容至内存消息,并继续提问
        UserMessage secondUsrMsg = UserMessage.from("3000块预算够吗？");
        chatMemory.add(firstResp.aiMessage(),secondUsrMsg);
        /**
         * 首次会话响应:
         *  北京两天虽短，但足够体验“古都气韵+皇城烟火”的精华。我帮你规划两条动线，兼顾经典与松弛感，可按体力灵活取舍。
         *
         * ---
         *
         * **🗓️ Day 1 · 中轴线皇城根儿（暴走模式）**
         * • **上午**：**天安门广场**（看升旗需单独查时间，平日可略）→ **故宫博物院**（提前7天官网抢票！从午门进，神武门出，只走中轴线+珍宝馆，约3.5小时）。
         * • **中午**：神武门出来，过马路到**景山公园**万春亭，俯瞰故宫全景，顺带解决简单午餐。
         * • **下午**：景山下来后，乘公交或骑共享单车到**北海公园**（划船看白塔）或**什刹海**（沿后海遛弯，逛烟袋斜街）。
         * • **傍晚**：从银锭桥走到**鼓楼**，在附近吃一顿铜锅涮肉（推荐南门涮肉或聚宝源，早点去不排队）。晚上体验**什刹海酒吧街**的灯火或直接回酒店休息。
         *
         * ✅ **今日重点**：故宫要赶早，景山一定要上，鼓楼下吃老北京小吃（爆肚、豆汁儿慎点）。
         *
         * ---
         *
         * **🗓️ Day 2 · 皇家园林＋胡同烟火（松弛版）**
         * • **上午**：**颐和园**（建议从东宫门进，沿昆明湖走到石舫，乘船至十七孔桥，重点游长廊和佛香阁外观，约3小时）。
         * • **中午**：在颐和园附近吃**西贝莜面村**或回市区吃烤鸭。
         * • **下午**：选其一——
         *   A. **天坛公园**（看祈年殿、回音壁，傍晚在古柏林里看大爷大妈踢毽），离天坛近可顺路去**前门大街**、**大栅栏**，体验铛铛车和北京老字号（吴裕泰茶饮）。
         *   B. 如果你更爱文艺，去**798艺术区**或**五道营胡同**，比南锣鼓巷人少，咖啡馆和设计师小店更好逛。
         * • **晚上**：**前门“局气”**或**四季民福烤鸭店**吃烤鸭（记得提前叫号），饭后**国家大剧院**外面拍夜景，或直接去**三里屯/国贸**感受现代北京。
         *
         * ✅ **今日重点**：颐和园要早去避开旅行团；烤鸭选“一人吃半只”不浪费；体力差可把天坛换成“陶然亭”轻松游。
         *
         * ---
         *
         * **⚠️ 避坑提示**
         * 1. 周一故宫、国博闭馆（法定节假日除外），规划避开。
         * 2. 住宿选在**地铁2号线沿线**（如前门、东单），去哪儿都方便。
         * 3. 别在故宫前门“一日游黑车”，地铁+共享单车最靠谱。
         * 4. 北京干燥，秋天是胡杨林季（建议去三环内就没时间了），带好口罩和防晒就够了。
         *
         * ---
         *
         * **🍽️ 美食速成地图**（顺路吃）
         * • **早餐**：护国寺小吃（豆腐脑＋糖耳朵）或老北京豆汁儿（尝鲜罢了）。
         * • **硬菜**：铜锅涮肉（清真）、烤鸭（大董/四季民福）、炸酱面（方砖厂69号）。
         * • **零嘴**：门钉肉饼、豌豆黄、冰糖葫芦。
         *
         * ---
         *
         * 如果你愿意告诉我具体到的季节、是否带老人小孩、更偏好历史还是美食/网红打卡，我可以再帮你精调路线。祝北京之行两天有滋有味！需要的话我还可以帮你写一个“极简清单版”存手机里。
         * */

        //5. 第二次大预言模型提问
        ChatResponse secondResp = chatModel.chat(chatMemory.messages());
        System.out.printf("\n 第二次会话响应:\n %s ",secondResp.aiMessage().text());
        /**
         * 第二次会话响应:
         *  3000元预算**够不够，关键看：这笔钱是“一个人在北京本地玩两天”的总花费，还是“包含从外地来北京的路费”。** 我分情况给你算笔明白账：
         *
         * ### 一、按“人已在北京，纯玩两天”来算 → 3000元完全够，甚至能小奢华一把
         *
         * 以我之前给你规划的动线为例（故宫+景山+北海+颐和园+天坛等），去掉往返大交通，单人花费大概是：
         *
         * | 项目 | 预算参考 |
         * |---|---|
         * | 住宿（经济型/如家、汉庭级别） | 300-450元/晚 × 2晚 ≈ **700-900元** |
         * | 门票（故宫60+景山2+北海15+颐和园30+天坛联票34） | **150元左右** |
         * | 吃饭（涮肉+烤鸭+小吃+早餐） | 200元/天 × 2 ≈ **400-600元**（吃点好的也够） |
         * | 市内交通（地铁+共享单车+偶尔打车） | **80-120元** |
         * | 零食咖啡纪念品 | **200-300元** |
         *
         * **总计：1500-2100元**，剩下的800-1500元可以作为弹性预算。想住好点儿的四合院精品酒店，或者吃一顿大董烤鸭，也可以cover住。
         *
         * **结论：这种场景下，3000元不是够，而是够得挺舒服。**
         *
         * ---
         *
         * ### 二、如果3000元包含“往返大交通” → 需要看你的出发地
         *
         * - **比如从天津/石家庄出发**，高铁往返300元内，那3000元也够。
         * - **比如从上海/武汉/长沙出发**，高铁往返1000-1400元，剩下1600-2000元在北京玩两天，只要住宿选经济型（如地铁站周边的快捷酒店），住差一点的也能勉强扛住，但会比较紧巴。
         * - **如果从广州/成都/乌鲁木齐坐飞机来**，往返机票正常要1500-2500元，扣掉后剩500-1500元在北京玩两天，只能住青旅床位，吃比较省，体验会打折扣。
         *
         * **结论：如果包含较远路费，建议把预算提高到3500-4500元，否则就别住核心区，住地铁终点站附近，把交通时间换金钱。**
         *
         * ---
         *
         * ### 三、如果是两个人共用3000元 → 不够，建议加到4500元以上
         *
         * 两个人摊住宿费很划算（单间300元/晚，人均才150），但吃饭、门票、交通都是双倍，基本要花掉：
         *
         * - 住宿：300-400元/晚 × 2 = **800元**
         * - 餐饮：300-500元/天 × 2 = **800-1000元**
         * - 门票：300元
         * - 市内交通：200元
         * - 零星购物：500元
         *
         * **两个人至少需要2500-3000元**，刚好能卡线，但一旦你想吃顿好烤鸭或住好点，就会超。所以两人带3000元会比较憋屈。
         *
         * ---
         *
         * ### 四、最后给你几个“省钱但体验不降级”的诀窍
         *
         * 1. **故宫门票一定提前7天在官网抢**，千万别找黄牛。
         * 2. **住宿别选前门/王府井**，选地铁2号线沿线（如鼓楼大街站、朝阳门站附近）或者7号线比较偏的地段，房价能便宜40%。
         * 3. **烤鸭别去全聚德**，吃“四季民福”或者“便宜坊”性价比高；涮肉也别只盯南门涮肉，“满福楼”或者虎坊桥附近的铜锅店都不错。
         * 4. **颐和园只买大门票**，不需要买联票；天坛买联票看祈年殿，但公园里的小景点可以放弃。
         * 5. **带好学生证/老年证**，门票折扣立省不少。
         *
         * ---
         *
         * 要不要告诉我你**从哪个城市出发、预算是否含大交通，以及是单人还是双人**？我可以给你一个更精确的“怎么花”方案，也方便帮你把住宿位置和线路重新搭配一下。
         * */

    }
}
