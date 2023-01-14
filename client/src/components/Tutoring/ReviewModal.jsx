import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button';
import { Textarea } from '../Input';
import styles from './ReviewModal.module.css';
import StarRating from './StarRating';

const ReviewModal = ({
  modalHandler,
  value,
  valueHandler,
  reviewData,
  setReviewData,
}) => {
  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
        <div className={styles.titleBox}>
          <div className={styles.title}>후기를 작성해주세요</div>
          <div className={styles.titleText}>
            후기 작성이 완료되면 과외 종료를 요청하게 됩니다.
          </div>
        </div>
        <div className={styles.starBox}>
          <div className={styles.starText}>
            튜터의 만족도를 별점으로 매겨주세요!
          </div>
          <div className={styles.stars}>
            <div className={styles.star}>
              <span>전문성</span>
              <StarRating
                reviewData={reviewData}
                setReviewData={setReviewData}
                name="professional"
              />
            </div>
            <div className={styles.star}>
              <span>준비성</span>
              <StarRating
                reviewData={reviewData}
                setReviewData={setReviewData}
                name="readiness"
              />
            </div>
            <div className={styles.star}>
              <span>설명력</span>
              <StarRating
                reviewData={reviewData}
                setReviewData={setReviewData}
                name="explantaion"
              />
            </div>
            <div className={styles.star}>
              <span>시간준수</span>
              <StarRating
                reviewData={reviewData}
                setReviewData={setReviewData}
                name="punctuality"
              />
            </div>
          </div>
        </div>
        <div className={styles.textBox}>
          <div>튜터에게 남기고 싶은 말이 있나요?</div>
          <Textarea
            id="confirmInput"
            placeHolder="튜터에게 남기고 싶은 말이 있나요?"
            value={value}
            handler={valueHandler}
          />
        </div>
        <div className={styles.buttonBox}>
          <ButtonNightBlue
            name="yes"
            buttonHandler={modalHandler}
            text="작성 완료"
          />
        </div>
      </div>
    </div>
  );
};

ReviewModal.propTypes = {
  modalHandler: PropTypes.func,
  value: PropTypes.string,
  valueHandler: PropTypes.func,
  placeHolder: PropTypes.string,
  score: PropTypes.array,
  setReviewData: PropTypes.func,
  reviewData: PropTypes.object,
};

export default ReviewModal;
