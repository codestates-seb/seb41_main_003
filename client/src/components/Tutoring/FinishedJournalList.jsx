import styles from './FinishedJournalList.module.css';
import dummyTutoringData from './dummyTutoringData';
import { HiSpeakerphone } from 'react-icons/hi';
import { MdEdit } from 'react-icons/md';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { ConfirmModal, ConfirmTextModal, AlertModal } from '../Modal';
import { useState } from 'react';
import EditReviewModal from './EditReviewModal';
import ReviewDetail from './ReviewDetail';

const FinishedJournalList = () => {
  //TODO:현재 이 컴포넌트는 튜티 기준으로 만들어짐 이후 튜터, 튜티 분기해 수정 필요
  //name부분과 과외 일지 작성 버튼 부분이 튜터와 튜티가 달라야 함
  // 작성된 리뷰 확인 버튼은 튜티인 경우에만 존재함

  const [alertModal, setAlertModal] = useState({
    text: '리뷰가 수정되었습니다.',
    isOpen: false,
  });

  const [confirmModal, setConfirmModal] = useState({
    text: `과외를 삭제 하시겠습니까?
    과외 삭제 시 과외 관리 내역이 모두 삭제됩니다.`,
    isOpen: false,
  });
  const [confirmTextModal, setConfirmTextModal] = useState({
    text: '과외 제목을 아래와 같이 수정합니다.',
    isOpen: false,
    value: '',
  });

  //리뷰 수정 상태
  //TODO: 여기 초기값으로 특정 과외 조회한 내용을 들려주어야 함...
  const [reviewData, setReviewData] = useState({
    isOpen: false,
    professional: 0,
    readiness: 0,
    explanation: 0,
    punctuality: 0,
    value: '',
  });

  const [reviewDetailData, setDetailReviewData] = useState(false);

  const {
    tutoringTitle,
    tuteeName,
    tutorName,
    dateNotices,
    updateAt,
    createAt,
    latestNotice,
  } = dummyTutoringData;

  const confirmHandler = (e) => {
    const { name } = e.target;
    setConfirmModal({
      ...confirmModal,
      isOpen: !confirmModal.isOpen,
    });

    if (name === 'yes') {
      //TODO : 과외 삭제 관련 API 요청(중요도:하)
      console.log('과외 삭제 요청 갑니다!');
    }
  };

  const confirmTextHandler = (e) => {
    const { name } = e.target;
    setConfirmTextModal({
      ...confirmTextModal,
      isOpen: !confirmTextModal.isOpen,
      value: '',
    });

    if (name === 'yes') {
      //TODO : 과외 제목 수정 관련 API 요청
      console.log(confirmTextModal.value);
    }
  };

  //리뷰 수정 모달
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

  const reviewDetailHandler = (e) => {
    const { name } = e.target;
    setDetailReviewData(!reviewDetailData);

    if (name === 'edit') {
      console.log('리뷰를 수정하러 갈거라네~');
      setReviewData({ ...reviewData, isOpen: !reviewData.isOpen });
    } else if (name === 'delete') {
      console.log('리뷰를 삭제할 거라네~');
      //TODO: 리뷰 삭제 로직 & 리뷰 삭제 완료되었다는 모달창 띄워야 함.
    }
  };
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
            <span>{tutorName}</span>
            <button onClick={confirmTextHandler}>
              <MdEdit className={styles.mdEdit} />
              <span>제목 수정하기</span>
            </button>
            {confirmTextModal.isOpen && (
              <ConfirmTextModal
                text={confirmTextModal.text}
                modalHandler={confirmTextHandler}
                value={confirmTextModal.value}
                valueHandler={(e) => {
                  setConfirmTextModal({
                    ...confirmTextModal,
                    value: e.target.value,
                  });
                }}
                placeHolder="새로운 과외 제목"
              />
            )}
          </div>
          <span className={styles.tutoringTitle}>{tutoringTitle}</span>
          <span className={styles.tutoringDate}>
            {`${new Date(createAt).toLocaleDateString()} ~ ${new Date(
              updateAt
            ).toLocaleDateString()}`}
          </span>
        </div>
        <div className={styles.buttonBox}>
          <ButtonNightBlue
            text="작성된 리뷰 확인"
            buttonHandler={reviewDetailHandler}
          />
          {reviewDetailData && (
            <ReviewDetail modalHandler={reviewDetailHandler} />
          )}
          {reviewData.isOpen && (
            <EditReviewModal
              modalHandler={reviewHandler}
              value={reviewData.value}
              valueHandler={(e) => {
                setReviewData({ ...reviewData, value: e.target.value });
              }}
              reviewData={reviewData}
              setReviewData={setReviewData}
            />
          )}
          <ButtonRed text="과외 삭제" buttonHandler={confirmHandler} />
          {confirmModal.isOpen && (
            <ConfirmModal
              text={confirmModal.text}
              modalHandler={confirmHandler}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default FinishedJournalList;
