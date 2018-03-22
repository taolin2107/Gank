package me.taolin.app.gank.ui.feedback

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avos.avoscloud.AVException
import com.avos.avoscloud.feedback.Comment
import com.avos.avoscloud.feedback.FeedbackAgent
import com.avos.avoscloud.feedback.FeedbackThread
import kotlinx.android.synthetic.main.fragment_feedback.*
import me.taolin.app.gank.App
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseFragment
import me.taolin.app.gank.ui.adapter.FeedbackAdapter
import me.taolin.app.gank.utils.KEY_USERNAME_FEEDBACK
import me.taolin.app.gank.utils.SharedPreferenceUtil

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/21
 * @description
 */
class FeedbackFragment : BaseFragment() {

    private val feedbackAgent = FeedbackAgent(App.instance)
    private val feedbackThread = feedbackAgent.defaultThread

    private lateinit var feedbackAdapter: FeedbackAdapter

    private val feedbackCallback = object : FeedbackThread.SyncCallback {
        override fun onCommentsSend(p0: MutableList<Comment>?, p1: AVException?) {
            feedbackAdapter.notifyDataSetChanged()
        }

        override fun onCommentsFetch(p0: MutableList<Comment>?, p1: AVException?) {
            feedbackAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedbackAdapter = FeedbackAdapter(feedbackThread.commentsList)
        feedbackList.adapter = feedbackAdapter
        feedbackList.layoutManager = LinearLayoutManager(context)

        sendBtn.setOnClickListener {
            sendFeedback(feedbackEditor.text.toString())
        }

        setFeedbackUserName()
        feedbackThread.sync(feedbackCallback)
    }

    private fun sendFeedback(feedback: String) {
        if (feedback.isNotEmpty()) {
            feedbackThread.add(Comment(feedback))
            feedbackAdapter.notifyDataSetChanged()
            feedbackList.smoothScrollToPosition(feedbackAdapter.itemCount)
            feedbackThread.sync(feedbackCallback)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setFeedbackUserName() {
        if (!SharedPreferenceUtil.readBoolean(KEY_USERNAME_FEEDBACK, false)) {
            feedbackThread.contact = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Build.MODEL + Build.getSerial()
            } else {
                Build.MODEL + Build.SERIAL
            }
            SharedPreferenceUtil.writeBoolean(KEY_USERNAME_FEEDBACK, true)
        }
    }
}