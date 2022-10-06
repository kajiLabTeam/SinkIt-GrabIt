package b22712.gasagasa

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime

class MainApplication: Application() {

    val LOGNAME: String = "MainApplication"

    // 気圧のログをとるかどうか
    private val pressureLog: Boolean = false

    // キュー
    val queue: Queue = Queue()

    lateinit var pressureSensor: PressureSensor
    lateinit var externalStorage: ExternalStorage
    lateinit var estimation: Estimation
    lateinit var vibratorAction: VibratorAction
    lateinit var fishEncounter: FishEncounter

    private val _push = MutableLiveData<Boolean>(false)
    val push: LiveData<Boolean> = _push
    fun setPush(boolean: Boolean) {
        _push.postValue(boolean)
//        Log.d(LOGNAME,"push is ".plus(push.value))
    }

    // 魚が存在しているかどうか
    private val _fishExist = MutableLiveData<Boolean>(false)
    val fishExist: LiveData<Boolean> = _fishExist
    fun setFishExist(boolean: Boolean) {
        _fishExist.postValue(boolean)
//        Log.d(LOGNAME,"fishExist is ".plus(fishExist.value))
    }

    override fun onCreate() {
        super.onCreate()

        pressureSensor = PressureSensor(this)
        fishEncounter = FishEncounter(this)
        estimation = Estimation(this)
        externalStorage = ExternalStorage(applicationContext)
        vibratorAction = VibratorAction(applicationContext)

        pressureSensor.start()
        fishEncounter.start()

        if (pressureLog) {
            pressureSensor.loging = true
            externalStorage.setQueue(queue)
            externalStorage.autoSave()
        }
    }
}