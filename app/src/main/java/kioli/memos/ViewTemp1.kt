package kioli.memos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kioli.memos.R

class ViewTemp1 : Fragment() {

    companion object {
        val ARG_TITLE_RES = "titleTab"

        fun getInstance(titleRes: Int): ViewTemp1 {
            val fragment = ViewTemp1()
            val args = Bundle()
            args.putInt(ViewTemp1.ARG_TITLE_RES, titleRes)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.view_fragment_placeholder, container, false)
    }
}