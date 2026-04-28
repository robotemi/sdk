package com.robotemi.sdk.sample.new_feature

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.constants.HardButton
import com.robotemi.sdk.listeners.OnButtonStatusChangedListener
import com.robotemi.sdk.listeners.OnRobotReadyListener
import com.robotemi.sdk.map.Floor
import com.robotemi.sdk.navigation.model.SpeedLevel
import com.robotemi.sdk.permission.Permission
import com.robotemi.sdk.sample.MainActivity
import com.robotemi.sdk.sample.databinding.ActivityTest137Binding
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener
import com.robotemi.sdk.serial.Serial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import com.robotemi.sdk.sample.MainActivity.Companion.HOME_BASE_LOCATION

class Test137Activity : AppCompatActivity(), OnRobotReadyListener {
    private lateinit var binding: ActivityTest137Binding
    private val robot = Robot.getInstance()
    private var currentButtonListener: OnButtonStatusChangedListener? = null
    private var currentSequenceListener: OnSequencePlayStatusChangedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest137Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initTestCases()
    }

    private fun initTestCases() {
        binding.ibBack.setOnClickListener { finish() }
        // TTS of all languages
        binding.btnTtsLanguages.setOnClickListener {
            val baseRequest = TtsRequest.create(speech = "", language = TtsRequest.Language.SYSTEM)
            // --- 1-10: Asia Pacific ---
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.EN_US.value,
                    speech = "Believe you can and you're halfway there.\n"
                )
            )
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.ZH_CN.value,
                    speech = "红豆生南国，春来发几枝。愿君多采撷，此物最相思。\n"
                )
            )
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.ZH_HK.value, speech = "一心一意，萬事如意。\n"
                )
            )
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.ZH_TW.value, speech = "精益求精，永不止步。\n"
                )
            )
            // Thai
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.TH_TH.value,
                    speech = "สวัสดีครับ ยินดีที่ได้รู้จัก\n"
                )
            )
            // Korean
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.KO_KR.value, speech = "어제보다 나은 내일.\n"
                )
            )
            // Japanese
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.JA_JP.value, speech = "七転び八起き。\n"
                )
            )
            // Indonesian
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.IN_ID.value,
                    speech = "Selamat pagi, apa kabar?\n"
                )
            )
            // Vietnamese
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.VI_VN.value,
                    speech = "Xin chào, rất vui được gặp bạn.\n"
                )
            )

            // --- 11-20: Europe ---
            // German
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.DE_DE.value, speech = "Guten Tag, mehr Licht!\n"
                )
            )
            // French
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.FR_FR.value, speech = "C'est la vie.\n"
                )
            )
            // Italian
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.IT_IT.value, speech = "La dolce vita.\n"
                )
            )
            // Polish
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.PL_PL.value, speech = "Wszystko jest możliwe.\n"
                )
            )
            // Russian
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.RU_RU.value, speech = "Привет, мир!\n"
                )
            )
            // Spanish
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.ES_ES.value,
                    speech = "La vida es corta, vívela.\n"
                )
            )
            // Greek
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.EL_GR.value, speech = "Γνώθι σεαυτόν.\n"
                )
            )

            // --- 21-30: Middle East & Others ---
            // Hebrew
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.HE_IL.value, speech = "שלום, מה שלומך?\n"
                )
            )
            // Arabic
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.AR_EG.value, speech = "مرحباً بكم في تيمي\n"
                )
            )
            // Turkish
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.TR_TR.value, speech = "Merhaba, nasılsınız?\n"
                )
            )
            // Hindi
            robot.speak(
                baseRequest.copy(
                    language = TtsRequest.Language.HI_IN.value, speech = "नमस्ते, आप कैसे हैं?\n"
                )
            )
            printLog("30 Languages queue started")
        }

        // Google TTS with 900+ words
        binding.btnGoogleLongTts.setOnClickListener {
            val longText = """
        The Giant Panda: A Comprehensive Study of Nature’s Most Beloved Ambassador.

        Introduction: The Global Icon of Conservation.
        The giant panda, known scientifically as Ailuropoda melanoleuca, is perhaps one of the most recognizable and cherished animals on the planet. Characterized by its striking black and white coat and its gentle, rolling gait, the giant panda is a species of bear native to the high-altitude mountain ranges of south-central China. While it belongs to the biological order Carnivora, the giant panda is a highly specialized herbivore, with a diet consisting almost exclusively of bamboo. For decades, the panda has served as a global icon for wildlife conservation, representing the fragile and complex balance between human civilization and the preservation of the natural world.

        Anatomy, Evolution, and the Unique "Pseudo-Thumb".
        A mature giant panda is a formidable and heavy creature, with males typically weighing between 85 and 125 kilograms, while females are slightly smaller. Their most distinctive physical feature is their thick, woolly fur, patterned with large patches of black on the ears, around the eyes, and across the shoulders and legs. Scientists believe this unique coloration serves a dual purpose: providing camouflage in snowy and rocky mountain environments, and acting as a form of social signaling to other pandas. 
        One of the most fascinating evolutionary adaptations of the panda is the "pseudo-thumb." This structure is actually an elongated radial sesamoid bone in the wrist that functions much like a human thumb. This allows the panda to grip, hold, and peel bamboo stalks with incredible dexterity and precision. This adaptation is a perfect example of convergent evolution, where a species develops a specific physical trait to occupy a unique ecological niche.

        The Science of the Bamboo-Centric Diet.
        Bamboo is the essential lifeblood of the giant panda. Despite having the digestive system and gut bacteria of a carnivore, pandas have adapted to derive nutrients from a plant that is notoriously low in energy and difficult to digest. To compensate for the poor nutritional quality of bamboo, an adult panda must consume a massive amount of food, ranging from 12 to 38 kilograms of bamboo every single day. They spend up to 14 or even 16 hours a day simply eating. 
        Their strong jaws and large, flat molar teeth are specifically designed to crush the tough, fibrous stalks of various bamboo species. Because they derive so little net energy from their diet, pandas have developed a low-metabolic lifestyle. They move slowly and spend most of their non-eating hours resting or sleeping to conserve every possible calorie. This evolutionary trade-off defines almost every aspect of their daily existence.

        Habitat, Geography, and the Misty Mountains.
        Historically, giant pandas roamed across a vast territory encompassing much of southern and eastern China, stretching into neighboring regions of Myanmar and Vietnam. However, due to centuries of human agricultural expansion and habitat fragmentation, they are now confined to six isolated mountain ranges in the Sichuan, Shaanxi, and Gansu provinces of China. 
        These mountain forests, such as the Minshan and Qinling ranges, are cool, moist, and often shrouded in mist, providing the perfect humid environment for various species of bamboo to flourish. The survival of the panda is intrinsically linked to the health of these high-altitude ecosystems. These forests also act as an "umbrella," protecting many other rare and endangered species that share the same habitat, such as the golden snub-nosed monkey and the red panda.

        Behavioral Patterns and the Solitary Life.
        Pandas are primarily solitary animals, preferring to spend their lives roaming their territories alone. They maintain well-defined home ranges, which they mark using scent glands located beneath their tails, as well as by scratching trees. Unlike many other bear species found in northern climates, giant pandas do not hibernate. Instead, they practice vertical migration, moving to lower altitudes in the winter to find warmer temperatures and moving higher in the summer to stay cool. 
        While they are generally peaceful and quiet, they possess a complex system of communication involving over a dozen different vocalizations. They emit bleats, barks, huffs, and growls to convey emotions or warnings, particularly during the brief and intense mating season when males and females must find each other in the dense forest.

        The Challenges of Reproduction and Infancy.
        One of the most significant hurdles in panda conservation is their exceptionally low reproductive rate. A female panda is fertile for a very narrow window—typically only 24 to 72 hours once a year. This makes natural breeding in the wild a statistically difficult process. When a cub is finally born after a gestation period of roughly five months, it is incredibly tiny and vulnerable. 
        A newborn cub weighs only about 100 grams, which is approximately 1/900th the size of its mother. It is born pink, blind, and nearly hairless. For the first few months of its life, the cub is entirely dependent on its mother’s constant care and protection. Because the maternal investment required to raise a cub is so intense, a female in the wild can usually only successfully rear one offspring every two to three years.

        Conservation Success and the Road to Recovery.
        The story of the giant panda is widely regarded as one of the most successful conservation efforts in modern history. In the 1980s, the species was on the absolute brink of extinction due to poaching and massive deforestation. However, the Chinese government, in partnership with international organizations like the WWF, established over 60 dedicated nature reserves and implemented strict legal protections. 
        Reforestation programs have helped reconnect isolated populations by creating "green corridors." As a result of these sustained efforts, the wild population has slowly rebounded to over 1,800 individuals. In 2016, the International Union for Conservation of Nature (IUCN) officially downgraded the panda's status from "Endangered" to "Vulnerable." While this was a major victory, new threats such as climate change and infrastructure development continue to pose risks to the long-term stability of their bamboo sources.

        Cultural Impact, Symbolism, and Panda Diplomacy.
        Beyond their biological importance, giant pandas hold a unique and prestigious place in global human culture. In China, the panda is a national treasure, symbolizing peace, harmony, and the balance of Yin and Yang. Internationally, the panda has become a tool of "panda diplomacy," where the Chinese government loans pandas to foreign zoos as a gesture of international friendship and scientific cooperation. 
        These programs have raised billions of dollars for conservation research and have fostered a worldwide passion for environmental protection. The panda’s image, famously used by the World Wildlife Fund since its inception in 1961, remains a powerful reminder of our collective responsibility to protect the Earth’s biodiversity.

        Conclusion: A Legacy of Hope and Stewardship.
        As we navigate the environmental challenges of the 21st century, the giant panda stands as a resilient symbol of hope. It reminds us of what we stand to lose through negligence, but more importantly, what we can save through dedicated science, international cooperation, and political will. By protecting the panda and its misty mountain home, we are protecting the ancient forests that regulate our climate and provide life-sustaining resources for millions of people. 
        The journey of the giant panda from the edge of extinction back toward a sustainable future is a testament to the power of human compassion and our ability to act as responsible stewards of the natural world. This concludes our extensive 1,000-word academic study and text-to-speech diagnostic performance test.
    """.trimIndent()
            val ttsListener = object : Robot.TtsListener {
                override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
                    runOnUiThread {
                        when (ttsRequest.status) {
                            TtsRequest.Status.PROCESSING -> {
                                binding.progressBar.visibility = View.VISIBLE
                                printLog("TTS Status: PROCESSING")
                            }

                            TtsRequest.Status.STARTED -> {
                                binding.progressBar.visibility = View.GONE
                                printLog("TTS Status: STARTED (Speaking)")
                            }

                            TtsRequest.Status.COMPLETED -> {
                                binding.progressBar.visibility = View.GONE
                                printLog("TTS Status: COMPLETED")
                                robot.removeTtsListener(this)
                            }

                            TtsRequest.Status.ERROR, TtsRequest.Status.CANCELED, TtsRequest.Status.NOT_ALLOWED -> {
                                binding.progressBar.visibility = View.GONE
                                printLog("TTS Status: ${ttsRequest.status}")
                                robot.removeTtsListener(this)
                            }

                            TtsRequest.Status.PENDING -> {
                                printLog("TTS Status: PENDING")
                            }
                        }
                    }
                }
            }
            robot.addTtsListener(ttsListener)
            printLog("Requesting Long TTS (1000+ words)...")
            robot.speak(TtsRequest.create(longText))
        }

        //  <break/> tag
        binding.btnBreakTag.setOnClickListener {
            val text = "Let's pause <break time=\"2000ms\"/> and continue."
            robot.speak(TtsRequest.create(text))
        }

        // Sequence Step Listener
        binding.btnSequenceStepListener.setOnClickListener {
            if (requestPermissionIfNeeded(
                    Permission.SEQUENCE, MainActivity.REQUEST_CODE_SEQUENCE_FETCH_ALL
                )
            ) {
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val sequences = robot.getAllSequences()
                    withContext(Dispatchers.Main) {
                        if (sequences.isNotEmpty()) {
                            currentSequenceListener?.let {
                                robot.removeOnSequencePlayStatusChangedListener(it)
                            }
                            val randomSequenceId = sequences.random().id
                            currentSequenceListener = object : OnSequencePlayStatusChangedListener {
                                override fun onSequencePlayStatusChanged(
                                    status: Int, sequenceId: String?
                                ) {
                                }

                                override fun onSequenceStepChanged(
                                    id: String, stepIndex: Int, totalSteps: Int
                                ) {
                                    runOnUiThread {
                                        val progressInfo = "Step: ${stepIndex} / $totalSteps"
                                        binding.tvSequenceStatus.text = progressInfo
                                        printLog("Sequence Progress: $progressInfo")
                                    }
                                }
                            }
                            currentSequenceListener?.let {
                                robot.addOnSequencePlayStatusChangedListener(it)
                            }
                            robot.playSequence(randomSequenceId)
//                            robot.playSequence(randomSequenceId, true)
                        } else {
                            printLog("Please create a sequence in temi Center")
                        }
                    }
                } catch (e: Exception) {
                    printLog("Error: ${e.message}")
                }
            }
        }

        // Sequence with Repeat
        binding.btnPlaySequenceRepeat.setOnClickListener {
            if (requestPermissionIfNeeded(
                    Permission.SEQUENCE, MainActivity.REQUEST_CODE_SEQUENCE_FETCH_ALL
                )
            ) {
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val sequences = robot.getAllSequences()
                    withContext(Dispatchers.Main) {
                        if (sequences.isNotEmpty()) {
                            currentSequenceListener?.let {
                                robot.removeOnSequencePlayStatusChangedListener(it)
                            }
                            val randomSequenceId = sequences.random().id
                            currentSequenceListener = object : OnSequencePlayStatusChangedListener {
                                override fun onSequencePlayStatusChanged(
                                    status: Int, sequenceId: String?
                                ) {
                                    runOnUiThread {
                                        when (status) {
                                            OnSequencePlayStatusChangedListener.PREPARING -> {
                                                binding.progressBar.visibility = View.VISIBLE
                                                printLog("Sequence Status: PREPARING")
                                            }

                                            OnSequencePlayStatusChangedListener.PLAYING -> {
                                                binding.progressBar.visibility = View.GONE
                                                printLog("Sequence Status: PLAYING")
                                            }

                                            OnSequencePlayStatusChangedListener.IDLE -> {
                                                binding.progressBar.visibility = View.GONE
                                                printLog("Sequence Status: IDLE (Completed)")
                                            }

                                            OnSequencePlayStatusChangedListener.ERROR -> {
                                                binding.progressBar.visibility = View.GONE
                                                printLog("Sequence Status: ERROR")
                                            }
                                        }
                                    }
                                }

                                override fun onSequenceStepChanged(
                                    id: String, stepIndex: Int, totalSteps: Int
                                ) {
                                    runOnUiThread {
                                        val progressInfo = "Step: ${stepIndex} / $totalSteps"
                                        binding.tvSequenceStatus.text = progressInfo
                                        printLog("Sequence Progress: $progressInfo")
                                    }
                                }
                            }
                            currentSequenceListener?.let {
                                robot.addOnSequencePlayStatusChangedListener(it)
                            }
                            robot.playSequence(randomSequenceId, repeat = 3)
//                            robot.playSequence(randomSequenceId, true, repeat = 3)
                        } else {
                            printLog("Please create a sequence in temi Center")
                        }
                    }
                } catch (e: Exception) {
                    printLog("Error: ${e.message}")
                }
            }
        }
        // Multi-Floor
        binding.btnMultiFloor.setOnClickListener {

            val options = arrayOf(
                "1. Get All Floors ",
                "2. Create New Floor ",
                "3. Delete Floor ",
                "4. Rename Floor ",
                "5. Get Map Data ",
                "6. Rename Location (Cross-Floor)",
                "7. Delete Location (Cross-Floor)"
            )

            AlertDialog.Builder(this).setTitle("Multi-Floor Management (137)")
                .setItems(options) { _, which ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        val floors = robot.getAllFloors()
                        val currentFloorId: Int? = robot.getCurrentFloor()?.id
                        printLog("currentFloorId: $currentFloorId")
                        when (which) {
                            // Get All Floors
                            0 -> {
                                if (floors.isEmpty()) {
                                    printLog("Floors List is empty.")
                                } else {
                                    val sb = StringBuilder("Floors List:\n")
                                    floors.forEach { sb.append("· [${it.id}] ${it.name}\n") }
                                    printLog(sb.toString())
                                }
                            }
                            // newFloor
                            1 -> {
                                val name = "Floor_${
                                    UUID.randomUUID().toString().substring(0, 8)
                                }"
                                val res = robot.newFloor(name, true)
                                val floors = robot.getAllFloors()
                                withContext(Dispatchers.Main) {
                                    val status = res?.let { if (it > 0) "Success" else "Failed" }
                                    printLog("newFloor: $name -> $status (Result: $res)")
                                    if (res == -405) printLog("Error -405: Please save the map first in the Map editor!")

                                    val sb = StringBuilder("After newFloor Floors List:\n")
                                    floors.forEach { sb.append("· [${it.id}] ${it.name}\n") }
                                    printLog(sb.toString())
                                }
                            }
                            // deleteFloor
                            2 -> {
                                val deletableFloors = floors.filter { it.id != currentFloorId }
                                if (deletableFloors.isNotEmpty()) {
                                    val targetId = deletableFloors.last().id
                                    val res = robot.deleteFloor(targetId)
                                    val floors = robot.getAllFloors()

                                    withContext(Dispatchers.Main) {
                                        printLog("deleteFloor -> ID: $targetId, ResultCode: $res")

                                        val sb = StringBuilder("After deleteFloor Floors List:\n")
                                        floors.forEach { sb.append("· [${it.id}] ${it.name}\n") }
                                        printLog(sb.toString())
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        printLog("Skip: Need at least 2 floors to test delete.")
                                    }
                                }
                            }
                            // renameFloor
                            3 -> {
                                if (floors.isNotEmpty()) {
                                    val randomFloor = floors.random()
                                    val targetId = randomFloor.id
                                    val oldName = randomFloor.name
                                    val newName = "Rename_${
                                        UUID.randomUUID().toString().substring(0, 8)
                                    }"
                                    val res = robot.renameFloor(targetId, newName)
                                    val floors = robot.getAllFloors()

                                    withContext(Dispatchers.Main) {
                                        val status =
                                            if (res == 200 || res == 0) "Success" else "Failed"
                                        printLog("renameFloor -> ID: $targetId, OldName: $oldName, NewName: $newName, Status: $status (Result: $res)")

                                        val renamedFloor = floors.find { it.id == targetId }
                                        if (renamedFloor != null) {
                                            printLog("Updated Floor Info: · [${renamedFloor.id}] ${renamedFloor.name}")
                                        } else {
                                            printLog("Error: Renamed floor not found in the updated list.")
                                        }
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        printLog("Skip: No floors available to rename.")
                                    }
                                }
                            }
                            //Get Map Data
                            4 -> {
                                if (floors.isNotEmpty()) {
                                    val randomFloor = floors.random()
                                    val targetId = randomFloor.id
                                    val targetName = randomFloor.name

                                    withContext(Dispatchers.Main) {
                                        printLog("getFloorAndMapData -> Requesting: $targetName (ID: $targetId)")
                                    }
                                    val data = robot.getFloorAndMapData(targetId)

                                    withContext(Dispatchers.Main) {
                                        if (data != null) {
                                            val floor = data.first
                                            val mapData = data.second
                                            val statusMsg = "Success: [$floor] \n mapData: $mapData"
                                            printLog("getFloorAndMapData -> $statusMsg")
                                        } else {
                                            printLog("getFloorAndMapData -> Failed: Result is null (Check if map is locked/saved)")
                                        }
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        printLog("Skip: No floors available to fetch data.")
                                    }
                                }
                            }
                            //Rename Location (Cross-Floor)
                            5 -> {
                                val targetFloor: Floor? = floors.find { it.id != currentFloorId }
                                if (targetFloor != null) {
                                    var oldName = ""
                                    if (targetFloor.locations.size <= 1) {
                                        withContext(Dispatchers.Main) {
                                            printLog("Please Ensure there are 2 locations targetFloor: $targetFloor")
                                        }
                                        return@launch
                                    } else if (targetFloor.locations.size > 1) {
                                        oldName =
                                            targetFloor.locations.filter { it.name != HOME_BASE_LOCATION }
                                                .random().name
                                    }

                                    withContext(Dispatchers.Main) {
                                        val sb = StringBuilder("Before rename location on floor:\n")
                                        sb.append("· [${targetFloor.id}] ${targetFloor.name} ${targetFloor.locations}\n")
                                        printLog(sb.toString())
                                    }

                                    val newName = oldName.plus("_new")
                                    printLog("oldname $oldName, newname $newName")
                                    val res = robot.renameLocationOnFloor(
                                        targetFloor.id, oldName, newName
                                    )

                                    val newTargetFloor =
                                        robot.getAllFloors().find { it.id == targetFloor.id }
                                    withContext(Dispatchers.Main) {
                                        printLog("renameLocationOnFloor -> Target: ${targetFloor.id}, Result: $res")
                                        printLog("----------------------------------------")

                                        val sb = StringBuilder("After rename location on floor:\n")
                                        sb.append("· [${newTargetFloor?.id}] ${newTargetFloor?.name} ${newTargetFloor?.locations}\n")
                                        printLog(sb.toString())
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        printLog("Skip: Need at least 2 floors to test cross-floor rename. Use renameLocation() for single floors")
                                    }
                                }
                            }
                            // deleteLocationOnFloor
                            6 -> {
                                val targetFloor: Floor? = floors.find { it.id != currentFloorId }
                                if (targetFloor != null) {
                                    val res =
                                        robot.deleteLocationOnFloor(targetFloor.id, "Temp_Position")
                                    printLog("deleteLocationOnFloor -> Target: ${targetFloor.id}, Result: $res")
                                } else {
                                    printLog("Skip: Cross-floor test needs another floor.")
                                }
                            }
                        }
                    }
                }.show()
        }
        binding.btnResetMap.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            printLog("resetMap -> Requesting... Please wait.")
            lifecycleScope.launch(Dispatchers.IO) {
                val resp = robot.resetMap(
                    saveHomeBaseIfCharging = true, allFloor = true
                )

                withContext(Dispatchers.Main) {
                    val status = if (resp == 200 || resp == 0) "Success" else "Failed"
                    printLog("resetMap: saveHomeBaseIfCharging=true, allFloor=true, status: $status")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        binding.btnImportMap.setOnClickListener {
            // This is possible but not recommended.
            // As Android doesn't recommend to use file:// scheme to send files.
            val sdcard = Environment.getExternalStorageDirectory()
            val file = File(sdcard, "temi/map-1769067374650.tar.gz")
            if (file.exists()) {
                Robot.getInstance().loadMapWithBackupFile(
                    Uri.fromFile(file),
                )
            } else {
                Toast.makeText(
                    this, "Please place a map file at public storage", Toast.LENGTH_SHORT
                ).show()
            }
        }
        // Adjuest volume with drawer
        binding.btnAdjuestVolumewithdrawer.setOnClickListener {
            if (requestPermissionIfNeeded(
                    Permission.SETTINGS, MainActivity.REQUEST_CODE_NORMAL
                )
            ) {
                return@setOnClickListener
            }
            val volume = (0..10).random()
            robot.setVolume(volume, true)
            printLog("Randomly setting volume to: $volume (Range: 0-10)")
        }

        // SpeedLevel   Get / Set / GoTo
        binding.btnSpeedLevel.setOnClickListener {
            // Get
            val currentSpeedLevel = robot.goToSpeed
            printLog("Get: Current GoToSpeedLevel is $currentSpeedLevel")

            // Set
            val levels = arrayOf(
                SpeedLevel.VERY_HIGH,
                SpeedLevel.HIGH,
                SpeedLevel.MEDIUM,
                SpeedLevel.SLOW,
                SpeedLevel.VERY_SLOW,
                SpeedLevel.customSpeed(0.8f)
            )
            val nextLevel = levels.random()
            robot.goToSpeed = nextLevel
            printLog("Set: New GoToSpeedLevel set to $nextLevel")

            // GoTo
            val locations = robot.locations
            if (locations.isNotEmpty()) {
                val target = locations.random()
                printLog("GoTo: Testing speed with target location: $target")
                robot.goTo(target)
            } else {
                printLog("GoTo: No locations found, please save a location to test navigation speed.")
            }
        }
        //LCD_Txt persist
        binding.btnLCDTxtPersist.setOnClickListener {
            val persist = if (it.tag == true) {
                it.tag = false
                false
            } else {
                it.tag = true
                true
            }
            val lcdText = if (persist) "PERSIST" else "CLEARED"
//            Robot.getInstance().sendSerialCommand(
//                Serial.CMD_LCD_TEXT,
//                getLcdBytes(lcdText) + getLcdPersistBytes(persist)
//            )

            //If you want to set the LCD text color and LCD background color, you can refer to the following code
            val lcdTextColor = if (persist) byteArrayOf(0xFF.toByte(), 0x00, 0x00) else byteArrayOf(
                0xFF.toByte(), 0xFF.toByte(), 0x00
            )
            val lcdBackgroundColor =
                if (persist) byteArrayOf(0x00, 0xFF.toByte(), 0x00) else byteArrayOf(
                    0x00, 0xFF.toByte(), 0xFF.toByte()
                )
            // Send command to control hardware components on temi GO
            val result = Robot.getInstance().sendSerialCommand(
                Serial.CMD_LCD_TEXT, Serial.getLcdBytes(
                    lcdText
                ) + Serial.getLcdColorBytes(
                    lcdTextColor, target = Serial.LCD.TEXT_0_COLOR
                ) + Serial.getLcdColorBytes(
                    lcdBackgroundColor, target = Serial.LCD.TEXT_0_BACKGROUND
                ) + Serial.getLcdPersistBytes(persist)
            )
            val status = if (result == 0) "Success" else "Failed"
            printLog("LCDTxtPersist: $lcdText -> $status ($result)")
        }

        //Emergency Button Broadcast
        binding.btnEmergencyButtonBroadcast.setOnClickListener {
            currentButtonListener?.let {
                robot.removeOnButtonStatusChangedListener(it)
                printLog("Removed previous button listener.")
            }
            currentButtonListener = object : OnButtonStatusChangedListener {
                override fun onButtonStatusChanged(
                    hardButton: HardButton, status: HardButton.Status
                ) {
                    runOnUiThread {
                        if (hardButton == HardButton.EMERGENCY_STOP) {
                            val msg = "hardButton Event: is $status"
                            printLog(msg)
                        }
                    }
                }
            }
            currentButtonListener?.let {
                robot.addOnButtonStatusChangedListener(it)
            }
            printLog("Start listening for Emergency button events, Now press the emergency button")
        }

        binding.btnClearLog.setOnClickListener { clearLog() }
    }

    @androidx.annotation.CheckResult
    private fun requestPermissionIfNeeded(
        permission: Permission, requestCode: Int
    ): Boolean {
        if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
            return false
        }
        robot.requestPermissions(listOf(permission), requestCode)
        return true
    }

    override fun onStart() {
        super.onStart()
        robot.addOnRobotReadyListener(this)
    }

    override fun onStop() {
        robot.removeOnRobotReadyListener(this)
        super.onStop()
    }

    override fun onRobotReady(isReady: Boolean) {
        if (isReady) robot.hideTopBar()
    }

    private fun printLog(msg: String, show: Boolean = true) {
        printLog("", msg, show)
    }

    private fun printLog(tag: String, msg: String, show: Boolean = true) {
        Log.d(tag.ifEmpty { "Test137Activity" }, msg)
        if (!show) return
        runOnUiThread {
            binding.tvLog.gravity = Gravity.BOTTOM
            binding.tvLog.append("· $msg \n")
        }
    }

    private fun clearLog() {
        binding.tvLog.text = ""
    }
}