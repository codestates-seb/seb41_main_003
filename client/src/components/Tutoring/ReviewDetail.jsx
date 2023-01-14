import styles from './ReviewDetail.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonRed } from '../Button';
import DetailStar from './DetailStar';

const reviewDetailData = {
  reviewId: 1,
  professional: 4,
  readiness: 4,
  explanation: 5,
  punctuality: 5,
  reviewBody: '음식이 친절하고 사장님이 맛있어요',
  tuteeName: '김학생',
  createAt: '2023-01-11T20:37:19.1594199',
  updateAt: '2023-01-11T20:37:19.1594199',
};

const ReviewDetail = ({ modalHandler }) => {
  //TODO: GET 매서드를 이용해서 특정 과외의 후기 조회 후 별점(number)을 차례로 담은 starRates 변수 생성
  const starRates = Object.values(reviewDetailData).slice(1, 5);

  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
        <div
          className={styles.title}
        >{`${reviewDetailData.tuteeName} 님이 남기신 후기입니다.`}</div>
        <div className={styles.starBox}>
          <div className={styles.stars}>
            <div className={styles.star}>
              <span>전문성</span>
              <DetailStar rate={starRates[0]} />
            </div>
            <div className={styles.star}>
              <span>준비성</span>
              <DetailStar rate={starRates[1]} />
            </div>
            <div className={styles.star}>
              <span>설명력</span>
              <DetailStar rate={starRates[2]} />
            </div>
            <div className={styles.star}>
              <span>시간준수</span>
              <DetailStar rate={starRates[3]} />
            </div>
          </div>
        </div>
        <div className={styles.textBox}>{reviewDetailData.reviewBody}</div>
        <div className={styles.buttonBox}>
          <ButtonNightBlue
            name="edit"
            buttonHandler={modalHandler}
            text="수정"
          />
          <ButtonRed name="delete" buttonHandler={modalHandler} text="삭제" />
        </div>
      </div>
    </div>
  );
};

ReviewDetail.propTypes = {
  modalHandler: PropTypes.func,
  value: PropTypes.string,
  valueHandler: PropTypes.func,
  placeHolder: PropTypes.string,
  score: PropTypes.array,
  setReviewData: PropTypes.func,
  reviewData: PropTypes.object,
};

export default ReviewDetail;
