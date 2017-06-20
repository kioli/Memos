package kioli.memos

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kioli.memos.db.NoteDataSource
import kotlinx.android.synthetic.main.view_text_to_speech.*
import java.util.*
import kotlin.collections.ArrayList

class ViewSpeechToText : Fragment() {

    private val SAVED_DATA = "saved list data"
    private val REQ_CODE_SPEECH_INPUT = 100
    private var items: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater!!.inflate(R.layout.view_text_to_speech, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        microphone.setOnClickListener { _ ->
            promptSpeechInput()
        }

        val notes = NoteDataSource.getNotes(context)
        notes.forEach {
            items.add(it.note)
        }

        list_notes.setHasFixedSize(true)
        list_notes.layoutManager = LinearLayoutManager(context)
        list_notes.adapter = NotesAdapter(items)
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            context.toastShort(getString(R.string.speech_not_supported))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.notes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.notes_edit -> context.toastShort("EDIT")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    NoteDataSource.insert(context, result[0])
                    items.add(result[0])
                    list_notes.adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(SAVED_DATA, list_notes.layoutManager.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        list_notes.layoutManager.onRestoreInstanceState(savedInstanceState?.getParcelable(SAVED_DATA))
    }
}
