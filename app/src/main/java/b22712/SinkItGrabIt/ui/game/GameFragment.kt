package b22712.SinkItGrabIt.ui.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import b22712.SinkItGrabIt.AnimationProvider
import b22712.SinkItGrabIt.MainApplication
import b22712.SinkItGrabIt.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    val LOGNAME = "GameFragment"

    private val gameViewModel: GameViewModel by viewModels{
        GameViewModelFactory((requireActivity().application as MainApplication))
    }

    private var _binding: FragmentGameBinding? = null
    private lateinit var app: MainApplication

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        app = (requireActivity().application as MainApplication)
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.setFragmentSize(view)

        app.inWater.observe(viewLifecycleOwner) {
            Log.d(LOGNAME, "inWater")
            if(it){
                gameViewModel.bubble(binding.bubble)
                gameViewModel.bubble(binding.bubble2)
                gameViewModel.bubble(binding.bubble3)
                gameViewModel.bubble(binding.bubble4)
                gameViewModel.bubble(binding.bubble5)
            } else {
                Log.d(LOGNAME, "outWater")
                gameViewModel.bubbleCrash()
            }
        }

        app.push.observe(viewLifecycleOwner) {
            gameViewModel.push(it)
        }

        app.fishExist.observe(viewLifecycleOwner){
            if(it){
                app.vibratorAction.vibrate(3000, 100)
            }
        }

        gameViewModel.isFishGrab.observe(viewLifecycleOwner) {
            gameViewModel.fishSilhouette(it, binding.imgSilhouette)
        }

        app.inWater.observe(viewLifecycleOwner) {
            if (!it && gameViewModel.isFishGrab.value!!) {
                gameViewModel.setFishGrab(false)
                gameViewModel.createGetFishFragment(this, binding.layoutResult.id)
            }
        }

        binding.buttonBuck.setOnClickListener {
            activity?.finish();
        }

        gameViewModel.isFishGrab.observe(viewLifecycleOwner) {
            if(it){
                binding.imgSilhouette.visibility = View.VISIBLE
            } else {
                binding.imgSilhouette.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GameFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}