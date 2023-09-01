package com.example.authorapp.presentation.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.authorapp.R
import com.example.authorapp.databinding.FragmentAuthorBinding


class AuthorFragment(
    private  val downloadUrl: String) : DialogFragment(){

    private lateinit var binding: FragmentAuthorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Light_NoActionBar_FullScreen)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorBinding.inflate(
            layoutInflater,
            container,
            false
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            Glide.with(binding.root.context).load(downloadUrl)
                .placeholder(R.drawable.placeholder_for_missing_posters).into(binding.imgAuthorPic)
            binding.ivClose.setOnClickListener {
                this.dismiss()
            }
    }


}