import styles from './ReviewDetail.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from '../Button';
import DetailStar from '../../util/DetailStar';
import { useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';

const ReviewDetailModal = ({ modalHandler, reviewDetail }) => {
  const starRates = Object.values(reviewDetail).slice(1, 5);
  const reset = useResetRecoilState(ModalState);

  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      aria-hidden
      role="dialog"
    >
      <div
        className={styles.title}
      >{`${reviewDetail.tuteeName} 님이 남기신 후기입니다.`}</div>
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
      <div className={styles.textBox}>{reviewDetail.reviewBody}</div>
      <div className={styles.buttonBox}>
        <ButtonNightBlue name="edit" buttonHandler={modalHandler} text="수정" />
        <ButtonSilver name="close" buttonHandler={() => reset()} text="닫기" />
      </div>
    </div>
  );
};

ReviewDetailModal.propTypes = {
  modalHandler: PropTypes.func,
  reviewDetail: PropTypes.object,
};

export default ReviewDetailModal;
