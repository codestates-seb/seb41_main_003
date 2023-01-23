import { useState } from 'react';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from '../Button';
import { Textarea } from '../Input';
import styles from './ReviewModal.module.css';
import StarRating from '../../util/StarRating';
import { useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';

const EditReviewModal = ({ modalHandler, initialReview }) => {
  const reset = useResetRecoilState(ModalState);
  const [value, setValue] = useState(initialReview.reviewBody);
  const [reviewData, setReviewData] = useState(initialReview);

  const valueHandler = (e) => {
    setValue(e.target.value);
  };

  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
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
              name="explanation"
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
      <div className={styles.editButtonBox}>
        <ButtonNightBlue
          name="yes"
          buttonHandler={(e) => modalHandler(e, value, reviewData)}
          text="수정 완료"
        />
        <ButtonSilver
          name="no"
          buttonHandler={() => reset()}
          text="수정 취소"
        />
      </div>
    </div>
  );
};

EditReviewModal.propTypes = {
  modalHandler: PropTypes.func,
  initialReview: PropTypes.object,
};

export default EditReviewModal;
