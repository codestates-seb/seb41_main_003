import styles from './ProfileCard.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue } from '../Button.jsx';
import { BlueSubject } from '../Subject.jsx';
import defaultUser from '../../assets/defaultUser.png';
import { MdStar } from 'react-icons/md';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';

const ProfileCard = ({ user }) => {
  const { name, rate, bio, school, subjects } = user;

  const setModal = useSetRecoilState(ModalState);
  const reset = useResetRecoilState(ModalState);

  const requiredProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '상대방에게 문의를 요청하시겠습니까?',
      modalHandler: () => {
        reset();
        //TODO:모달창에서 확인 버튼을 누르면 MessagePage로 이동
      },
    },
  };

  return (
    <div className={styles.cardContainer}>
      {/* //! API 연결 시 유저 이미지 수정 필요 */}
      <img alt="user img" src={defaultUser} />
      <section className={styles.textContainer}>
        <div className={styles.starLine}>
          <h3 className={styles.font1}>{name}</h3>
          <div className={styles.starBox}>
            <MdStar fill="#F0C24D" size="21" />
            <p className={styles.paragragh}>{rate} / 5 </p>
          </div>
        </div>
        <div>
          <p className={styles.font4}>한 줄 소개</p>
          <p className={styles.paragragh}>{bio}</p>
        </div>
        <div>
          <p className={styles.font4}>학교</p>
          <p className={styles.paragragh}>{school}</p>
        </div>
        <div>
          <p className={styles.font4}>과목</p>
          <span className={styles.subjects}>
            <ul>
              {subjects.map(({ subjectId, subjectTitle }) => {
                return <BlueSubject key={subjectId} text={subjectTitle} />;
              })}
            </ul>
          </span>
        </div>
      </section>
      <div className={styles.buttonBox}>
        <ButtonNightBlue
          buttonHandler={(e) => {
            e.preventDefault();
            setModal(requiredProps);
          }}
          text="문의하기"
        />
      </div>
    </div>
  );
};

ProfileCard.propTypes = {
  isAnnounceOn: PropTypes.func,
  QuestionModalHandler: PropTypes.func,
  user: PropTypes.object,
};

export default ProfileCard;
