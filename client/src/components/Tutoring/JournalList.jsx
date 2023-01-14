import styles from './JournalList.module.css';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import dummyTutoringData from './dummyTutoringData';
import { ConfirmValiModal, AlertModal, ConfirmTextModal } from '../Modal';
import { useState } from 'react';
import ReviewModal from './ReviewModal';

const JournalList = () => {
  const [alertModal, setAlertModal] = useState({
    text: '상대방에게 과외 종료가 요청되었습니다 \n 상대방이 종료를 수락하면 과외가 종료됩니다',
    isOpen: false,
  });
  const [valModal, setValModal] = useState({
    text: '과외 종료를 원하신다면 \n 아래의 입력창에 "과외 종료" 를 입력 후 \n 확인 버튼을 눌러주세요.',
    validation: '과외 종료',
    isOpen: false,
  });
  const [confirmModal, setConfirmModal] = useState({
    text: '과외 제목을 아래와 같이 수정합니다.',
    isOpen: false,
    value: '',
  });

  const [reviewData, setReviewData] = useState({
    isOpen: false,
    professional: 0,
    readiness: 0,
    explanation: 0,
    punctuality: 0,
    value: '',
  });

  const {
    tutoringTitle,
    tuteeName,
    tutorName,
    dateNotices,
    updateAt,
    createAt,
    latestNotice,
  } = dummyTutoringData;

  const valModalHandler = (e) => {
    const { name } = e.target;
    setValModal({
      ...valModal,
      isOpen: !valModal.isOpen,
      value: '',
    });
    if (name === 'yes') {
      // TODO : 과외 종료 관련 API 연결 필요
      setReviewData({ ...reviewData, isOpen: !reviewData.isOpen });
    }
  };

  const confirmHandler = (e) => {
    const { name } = e.target;
    setConfirmModal({
      ...confirmModal,
      isOpen: !confirmModal.isOpen,
      value: '',
    });

    if (name === 'yes') {
      //TODO : 과외 제목 수정 관련 API 요청
      console.log(confirmModal.value);
    }
  };

  const reviewHandler = (e) => {
    const { name } = e.target;
    setReviewData({
      ...reviewData,
      isOpen: !reviewData.isOpen,
      value: '',
    });
    if (name === 'yes') {
      //TODO: 과외 리뷰 작성 API 요청
      console.log(reviewData);
      setAlertModal({ ...alertModal, isOpen: !alertModal.isOpen });
    }
  };

  //TODO:현재 이 컴포넌트는 튜터 기준으로 만들어짐 이후 튜터, 튜티 분기해 수정 필요
  //name부분과 과외 일지 작성 버튼 부분이 튜터와 튜티가 달라야 함
  // 과외 일지 작성 버튼은 튜터인 경우에만 존재함

  return (
    <div className={styles.container}>
      <div className={styles.leftCard}>
        <div className={styles.noti}>
          <HiSpeakerphone className={styles.icon} />
          {latestNotice > 20
            ? `최근 공지사항 | ${latestNotice.slice(0, 20)}...`
            : `최근 공지사항 | ${latestNotice}`}
        </div>
        <ul className={styles.list}>
          {dateNotices.map((el) => (
            <li key={el.dateNoticeId} className={styles.li}>
              <div className={styles.dateBox}>
                <span className={styles.day}>
                  {new Date(el.startTime).getDate()}
                </span>
                <span className={styles.yearMonth}>{`${new Date(
                  el.startTime
                ).getFullYear()}년 ${
                  new Date(el.startTime).getMonth() + 1
                }월`}</span>
              </div>
              <div className={styles.textBox}>
                <span
                  className={styles.goal}
                >{`학습 목표 | ${el.dateNoticeTitle} `}</span>
                <span
                  className={styles.homework}
                >{`과제 제출 완료 (${el.finishHomeworkCount}/${el.homeworkCount})`}</span>
              </div>
              {/* TODO: 최근 공지를 눌렀을 때 해당 공지가 있는 일지 상세 페이지로 이동해야 함  */}
              <div className={styles.notiIcon}>
                <HiSpeakerphone className={styles.hiSpeaker} />
                공지
              </div>
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.rightCard}>
        <div className={styles.rightTextBox}>
          <div className={styles.nameBox}>
            {/* TODO: user status에 따라 tutorName이 뜨거나 tuteeName이 떠야 함  */}
            <span>{tuteeName}</span>
            <button onClick={confirmHandler}>
              <MdEdit className={styles.mdEdit} />
              <span>제목 수정하기</span>
            </button>
            {confirmModal.isOpen && (
              <ConfirmTextModal
                valueHandler={(e) => {
                  setConfirmModal({ ...confirmModal, value: e.target.value });
                }}
                text={confirmModal.text}
                value={confirmModal.value}
                modalHandler={confirmHandler}
                placeHolder="새로운 과외 제목"
              />
            )}
          </div>
          <span className={styles.tutoringTitle}>{tutoringTitle}</span>
          <span className={styles.tutoringDate}>
            {`${new Date(createAt).toLocaleDateString()} ~`}
          </span>
        </div>
        <div className={styles.buttonBox}>
          {/* TODO: 버튼 눌렀을 때 과외 일지 작성 페이지로 이동  */}
          <ButtonNightBlue text="과외 일지 작성" />
          {/* TODO: 만약 과외 종료 요청을 보내고 대기 중인 상태라면 AlertModal을 띄워야 함 */}
          <ButtonRed text="과외 종료" buttonHandler={valModalHandler} />
          {valModal.isOpen && (
            <ConfirmValiModal
              text={valModal.text}
              placeHolder="과외 종료"
              validation={valModal.validation}
              modalHandler={valModalHandler}
            />
          )}
          {reviewData.isOpen && (
            <ReviewModal
              modalHandler={reviewHandler}
              value={reviewData.value}
              valueHandler={(e) => {
                setReviewData({ ...reviewData, value: e.target.value });
              }}
              reviewData={reviewData}
              setReviewData={setReviewData}
            />
          )}
          {alertModal.isOpen && (
            <AlertModal
              text={alertModal.text}
              modalHandler={() =>
                setAlertModal({ ...alertModal, isOpen: !alertModal.isOpen })
              }
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default JournalList;