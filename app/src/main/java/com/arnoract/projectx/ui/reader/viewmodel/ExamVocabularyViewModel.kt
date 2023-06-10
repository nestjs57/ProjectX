package com.arnoract.projectx.ui.reader.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnoract.projectx.ui.reader.model.UiParagraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*

class ExamVocabularyViewModel : ViewModel() {

    private var uiParagraph = MutableLiveData<List<UiParagraph>>()
    private var textToSpeech: TextToSpeech? = null
    private val exams = MutableLiveData<MutableList<UiParagraph>>()
    private val _isClickable = MutableLiveData<Boolean?>()

    private val _currentParagraph = MutableLiveData<Int>()
    val currentParagraph: LiveData<Int>
        get() = _currentParagraph

    private val _currentIndexTakingExam = MutableLiveData(0)
    val currentIndexTakingExam: LiveData<Int>
        get() = _currentIndexTakingExam

    private val _currentVocab = MutableLiveData<String>()
    val currentVocab: LiveData<String>
        get() = _currentVocab

    private val _answerA = MutableLiveData<String>()
    val answerA: LiveData<String>
        get() = _answerA

    private val _answerB = MutableLiveData<String>()
    val answerB: LiveData<String>
        get() = _answerB

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _correctState = MutableLiveData<UiCorrectState?>()
    val correctState: LiveData<UiCorrectState?>
        get() = _correctState

    private val _totalWrong = MutableLiveData(0)
    val totalWrong: LiveData<Int>
        get() = _totalWrong

    private val _isShowTextTestAgain = MutableLiveData<Boolean>()
    val isShowTextTestAgain: LiveData<Boolean>
        get() = _isShowTextTestAgain

    private val _isShowTextNextParagraph = MutableLiveData<Boolean>()
    val isShowTextNextParagraph: LiveData<Boolean>
        get() = _isShowTextNextParagraph

    private val _nextParagraph = MutableSharedFlow<Unit>()
    val nextParagraph: MutableSharedFlow<Unit>
        get() = _nextParagraph

    private val _isShowFireWork = MutableLiveData<Boolean>()
    val isShowFireWork: LiveData<Boolean>
        get() = _isShowFireWork

    fun setUiParagraphs(data: List<UiParagraph>) {
        uiParagraph.value = data
    }

    fun setUpExam() {
        viewModelScope.launch {
            _isShowTextNextParagraph.value = false
            _isShowFireWork.value = false
            _totalWrong.value = 0
            _isShowTextTestAgain.value = false
            _currentIndexTakingExam.value = 0
            regenerateExam()
            initExam()
        }
    }

    fun onNextParagraph() {
        viewModelScope.launch {
            nextParagraph.emit(Unit)
        }
    }

    private fun initExam() {
        viewModelScope.launch {
            _isClickable.value = true
            _correctState.value = null
            _currentParagraph.value =
                exams.value?.get(_currentIndexTakingExam.value ?: 0)?.paragraphNum
            _isLoading.value = true
            delay(500)
            setVocab()
            setAnswer()
            _isLoading.value = false
        }
    }

    private fun regenerateExam() {
        val list = uiParagraph.value?.filter { vocab ->
            vocab.vocabulary.count { it == ' ' } <= 2 && vocab.translate.isNotBlank()
        }?.shuffled()?.take(10)?.map {
            it.copy(
                vocabulary = it.vocabulary.replace(",", "").replace(".", "")
            )
        }
        exams.value = list?.toMutableList()
    }

    private fun setCurrentIndex(i: Int) {
        _currentIndexTakingExam.value = i
    }

    private fun setVocab() {
        _currentVocab.value = exams.value?.get(_currentIndexTakingExam.value ?: 0)?.vocabulary
    }

    private fun setAnswer() {
        val trueAnswer = exams.value?.get(_currentIndexTakingExam.value ?: 0)?.translate ?: ""
        val wrongAnswer =
            exams.value?.filter { it.translate != trueAnswer }?.shuffled()?.firstOrNull()?.translate
                ?: ""
        if (randomTrueAnswer() == 1) {
            _answerA.value = trueAnswer
            _answerB.value = wrongAnswer
        } else {
            _answerA.value = wrongAnswer
            _answerB.value = trueAnswer
        }
    }

    fun onTextSpeech(context: Context) {
        if (_currentVocab.value.isNullOrBlank()) return
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.ENGLISH
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        _currentVocab.value,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED
                    )
                }
            }
        }
    }

    private fun randomTrueAnswer(): Int {
        return (0..3).random()
    }

    private fun removeIndex(index: Int) {
        exams.value?.removeAt(index)
    }

    fun checkAnswer(choice: Int) {
        viewModelScope.launch {
            if (_isClickable.value == false) return@launch
            _isClickable.value = false
            val answer = exams.value?.get(_currentIndexTakingExam.value ?: 0)?.translate
            if (choice == 1) {
                if (_answerA.value == answer) {
                    _correctState.value = UiCorrectState.CorrectA
                } else {
                    _correctState.value = UiCorrectState.InCorrectA
                    if (_totalWrong.value == 3) {
                        delay(500)
                        _isShowTextTestAgain.value = true
                        return@launch
                    }
                    _totalWrong.value = _totalWrong.value?.plus(1)
                }
            } else {
                if (_answerB.value == answer) {
                    _correctState.value = UiCorrectState.CorrectB
                } else {
                    _correctState.value = UiCorrectState.InCorrectB
                    if (_totalWrong.value == 3) {
                        delay(500)
                        _isShowTextTestAgain.value = true
                        return@launch
                    }
                    _totalWrong.value = _totalWrong.value?.plus(1)
                }
            }
            if (_currentIndexTakingExam.value == exams.value?.size?.minus(1)) {
                _isShowFireWork.value = true
                delay(500)
                _isShowTextNextParagraph.value = true
            } else {
                onNextVocab()
            }
        }
    }

    private fun onNextVocab() {
        viewModelScope.launch {
            delay(500)
            setCurrentIndex(_currentIndexTakingExam.value?.plus(1) ?: 0)
            initExam()
        }
    }
}

sealed class UiCorrectState {
    object CorrectA : UiCorrectState()
    object CorrectB : UiCorrectState()
    object InCorrectA : UiCorrectState()
    object InCorrectB : UiCorrectState()
}