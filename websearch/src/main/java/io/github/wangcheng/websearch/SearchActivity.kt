package io.github.wangcheng.websearch

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class SearchActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val action = intent.action
        val query: String? = if (Intent.ACTION_PROCESS_TEXT == action) {
            intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        } else if (Intent.ACTION_SEARCH == action || Intent.ACTION_WEB_SEARCH == action) {
            intent.getStringExtra(SearchManager.QUERY)
        } else {
            finish()
            return
        }
        val resultIntent = getSearchIntent(query)
        if (resultIntent.resolveActivity(packageManager) != null) {
            startActivity(resultIntent)
        }
        finish()
    }

    companion object {
        fun getSearchIntent(query: String?): Intent {
            val urlFromQuery = Uri.parse(query)
            if (urlFromQuery.isAbsolute) {
                return Intent(Intent.ACTION_VIEW, urlFromQuery)
            }
            val builder = Uri.Builder()
            val url = builder.scheme("https")
                .authority("www.google.com")
                .appendPath("search")
                .appendQueryParameter("q", query)
                .build()
            return Intent(Intent.ACTION_VIEW, url)
        }
    }
}
