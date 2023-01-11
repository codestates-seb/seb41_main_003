import styles from './Admin.module.css';
import defaultUser from '../assets/defaultUser.png';
import { MdEdit, MdDelete, MdAddCircle } from 'react-icons/md';
import { Link } from 'react-router-dom';

const profileLists = [
  {
    profileId: 1,
    name: '유영민',
    url: defaultUser,
    school: '서울대학교',
  },
  {
    profileId: 2,
    name: '신승구',
    url: defaultUser,
    school: '서울대학교',
  },
  {
    profileId: 3,
    name: '김민경',
    url: defaultUser,
    school: '서울대학교',
  },
  {
    profileId: 4,
    name: '이수영',
    url: defaultUser,
    school: '서울대학교',
  },
];

const Admin = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.title}>전체 프로필 관리</div>
        <ul className={styles.profileContainer}>
          {profileLists.map((el) => (
            <li key={el.profileId} className={styles.profileBox}>
              <div className={styles.front}>
                <img
                  alt="프로필 사진"
                  src={el.url}
                  className={styles.profileImg}
                />
                <div className={styles.profileTextBox}>
                  <Link to="/tuteeprofile">
                    {/* TODO: 튜터/튜티 여부 포함하는 데이터에 따라 Link 경로의 분기 필요 */}
                    <span className={styles.name}>{el.name}</span>
                  </Link>
                  <span className={styles.school}>{el.school}</span>
                </div>
              </div>
              <div className={styles.iconsBox}>
                <MdEdit id={el.profileId} className={styles.mdEdit} />
                <MdDelete className={styles.mdDelete} />
              </div>
            </li>
          ))}
          <Link to="/addprofile">
            <li className={styles.addBox}>
              <MdAddCircle className={styles.addIcon} />
              <span className={styles.addText}>프로필 추가하기</span>
            </li>
          </Link>
        </ul>
      </div>
    </div>
  );
};

export default Admin;
