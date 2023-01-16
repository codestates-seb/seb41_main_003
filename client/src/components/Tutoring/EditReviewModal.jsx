import styles from './ReviewModal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from '../Button';
import { Textarea } from '../Input';
import StarRating from './StarRating';

//TODO: GET 매서드로 '특정 과외 후기 조회' 요청을 보낸 뒤 받은 응답을 이용해 이미 작성되어 있던 별점과 코멘트를 뿌려준다.
const EditReviewModal = ({
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
        <div className={styles.editButtonBox}>
          <ButtonNightBlue
            name="yes"
            buttonHandler={modalHandler}
            text="수정 완료"
          />
          <ButtonSilver
            name="no"
            buttonHandler={modalHandler}
            text="수정 취소"
          />
        </div>
      </div>
    </div>
  );
};

EditReviewModal.propTypes = {
  modalHandler: PropTypes.func,
  value: PropTypes.string,
  valueHandler: PropTypes.func,
  placeHolder: PropTypes.string,
  score: PropTypes.array,
  setReviewData: PropTypes.func,
  reviewData: PropTypes.object,
};

export default EditReviewModal;
