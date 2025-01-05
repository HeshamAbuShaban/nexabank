package com.nexabank.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.nexabank.R
import com.nexabank.databinding.ViewCategoryCardBinding

class CategoryCard @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private var exampleString: String? = null
    private var exampleDrawable: Int = 0

    private val binding: ViewCategoryCardBinding =
        ViewCategoryCardBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        init()
    }

    private fun init() {
        // Load attributes
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.CategoryCard, defStyleAttr, 0
        )

        exampleString = typedArray.getString(R.styleable.CategoryCard_title)
        exampleDrawable = typedArray.getResourceId(R.styleable.CategoryCard_logo, 0)

        // Set text and compound drawable
        with(binding.sccTV) {
            text = exampleString
            setCompoundDrawablesWithIntrinsicBounds(0, exampleDrawable, 0, 0)
        }

        typedArray.recycle()
    }
}