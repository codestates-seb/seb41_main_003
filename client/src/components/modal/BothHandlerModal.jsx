import styles from './DefaultModal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from '../Button';

const BothHandlerModal = ({ text, modalHandler }) => {
  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <div className={styles.buttonBox}>
        <ButtonNightBlue name="yes" buttonHandler={modalHandler} text="확인" />
        <ButtonSilver name="no" buttonHandler={modalHandler} text="취소" />
      </div>
    </div>
  );
};

BothHandlerModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

export default BothHandlerModal;
