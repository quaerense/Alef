package org.quaerense.alef

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

class FullScreenImageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivFullScreenImage: ImageView = view.findViewById(R.id.ivFullScreenImage)
        val imageUrl = requireArguments().getString(IMAGE_URL)
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.image_not_found)
            .into(ivFullScreenImage)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(IMAGE_URL)) {
            Toast.makeText(context, R.string.error_image_is_not_found, Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }
    }

    companion object {

        private const val IMAGE_URL = "url"

        fun getInstance(imageUrl: String): FullScreenImageFragment {
            return FullScreenImageFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URL, imageUrl)
                }
            }
        }
    }

}