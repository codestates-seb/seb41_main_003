import styles from './Toggle.module.css';
import PropTypes from 'prop-types';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';

const Toggle = ({ isAnnounceOn, setIsAnnounceOn }) => {
  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);

  const requiredProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '공고 상태를 변경하시겠습니까?',
      modalHandler: (e) => {
        setIsAnnounceOn((prev) => (prev === 'REQUEST' ? 'NONE' : 'REQUEST'));
        reset();
        //TODO: 수정된 공고 상태로 변경해달라는 API 요청을 보내야 함.
        //공고 상태 수정 PATCH 요청으로 body에 wantedStatus 값으로 isAnnounceOn 값을 보내면 됨
      },
    },
  };

  return (
    <div className={styles.toggleSwitch}>
      <button
        onClick={(e) => {
          e.preventDefault();
          setModal(requiredProps);
        }}
      >
        <input
          className={styles.checkbox}
          type="checkbox"
          defaultChecked={isAnnounceOn === 'REQUEST' ? true : false}
        />
        <span
          className={`${styles.slider} ${
            isAnnounceOn === 'REQUEST' ? '' : styles.unActive
          }`}
        ></span>
      </button>
    </div>
  );
};

Toggle.propTypes = {
  modalOpenOnHandler: PropTypes.func,
  isAnnounceOn: PropTypes.string,
  setIsAnnounceOn: PropTypes.func,
};

export default Toggle;
