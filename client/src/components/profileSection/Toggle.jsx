import styles from './Toggle.module.css';
import PropTypes from 'prop-types';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../../recoil/modal';
import { useParams } from 'react-router-dom';
import axios from 'axios';
// import Profile from '../../recoil/profile';

const Toggle = ({ user, setUser }) => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  //TODO: useParams 말고 저장되어 있는 myprofileId를 사용해야 할 듯
  // const { profileId } = useRecoilValue(Profile);
  const { profileId } = useParams();

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '공고 상태를 변경하시겠습니까?',
      modalHandler: () => {
        setUser({
          ...user,
          wantedStatus: user.wantedStatus === 'NONE' ? 'REQUEST' : 'NONE',
        });
        patchWantedStatus();
        console.log(user.wantedStatus);
        resetModal();
      },
    },
  };

  const patchWantedStatus = async () => {
    await axios
      .patch(
        `${process.env.REACT_APP_BASE_URL}/profiles/status/${profileId}`,
        {
          wantedStatus: user.wantedStatus,
        },
        {
          headers: { Authorization: sessionStorage.getItem('authorization') },
        }
      )
      .then((res) => console.log(res.data.data))
      .catch((err) => console.log(err));
  };

  return (
    <div className={styles.toggleSwitch}>
      <button
        onClick={(e) => {
          e.preventDefault();
          setModal(confirm);
        }}
      >
        <input
          className={styles.checkbox}
          type="checkbox"
          defaultChecked={user.wantedStatus === 'REQUEST' ? true : false}
        />
        <span
          className={`${styles.slider} ${
            user.wantedStatus === 'REQUEST' ? '' : styles.unActive
          }`}
        ></span>
      </button>
    </div>
  );
};

Toggle.propTypes = {
  user: PropTypes.object,
  setUser: PropTypes.func,
};

export default Toggle;
