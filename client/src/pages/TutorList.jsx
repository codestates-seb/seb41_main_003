import { SnowMenuButton } from '../components/MainSection/MenuButton';
import FeedItem from '../components/MainSection/FeedItem';
import styles from '../pages/TutorList.module.css';
import { MdSearch, MdFilterList } from 'react-icons/md';
import defaultUser from '../assets/defaultUser.png';
import { useState } from 'react';
import FilterDropdown from '../components/MainSection/FilterDropdown';
import { Link } from 'react-router-dom';

const TutorListData = [
  {
    profileId: 1,
    name: '어때요',
    rate: 4.8,
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '수학',
      },
      {
        subjectId: 2,
        subjectTitle: '영어',
      },
    ],
    school: 'MIT',
    bio: '대치동 원탑 수학 머신',
    profileImage: {
      profileImageId: 1,
      url: defaultUser,
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
  {
    profileId: 2,
    name: '칸쵸',
    rate: 3.5,
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '영어',
      },
      {
        subjectId: 2,
        subjectTitle: '국어',
      },
      {
        subjectId: 3,
        subjectTitle: '사회',
      },
    ],
    school: '하버드',
    bio: '칸트 친구로 일 분 일 초를 낭비하는 것을 싫어하는 편입니다.',
    profileImage: {
      profileImageId: 1,
      url: defaultUser,
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
  {
    profileId: 3,
    name: '마이크',
    rate: 4.8,
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '자격증',
      },
    ],
    school: '서울 사이버 대학교',
    bio: '센프란시스코 빌딩 건물주',
    profileImage: {
      profileImageId: 1,
      url: defaultUser,
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
  {
    profileId: 4,
    name: '하이머',
    rate: 3,
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '기타',
      },
    ],
    school: 'Geothe Universitaet',
    bio: 'Hallo! Wie geht es Ihnen?',
    profileImage: {
      profileImageId: 1,
      url: defaultUser,
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
  {
    profileId: 5,
    name: '세라핀',
    rate: 5,
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '사회',
      },
    ],
    school: '북경대',
    bio: '주인공이 되는 방법을 알려드립니다!',
    profileImage: {
      profileImageId: 1,
      url: defaultUser,
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
  {
    profileId: 6,
    name: '햄토리',
    rate: 3,
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '국어',
      },
    ],
    school: '한국외대 한국어과',
    bio: '한국어를 배우기 위해 고군분투했던 나날들을 경험삼아 가르칩니다!',
    profileImage: {
      profileImageId: 1,
      url: defaultUser,
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
];

//ToDo: 필터 버튼이 눌릴 때마다 (onClick value 등이 바뀔 때마다) - 아마 useEffect 사용할듯
//tutorData 새롭게 갱신될 수 있도록 해당 필터 API 데이터 통신과 연결 필요함

const TutorList = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [isClicked, setIsClicked] = useState([]);

  const filterHandler = () => {
    setIsOpen(!isOpen);
  };

  const clickHandler = (e) => {
    if (isClicked.includes(e.target.value)) {
      setIsClicked(isClicked.filter((el) => el !== e.target.value));
    } else {
      setIsClicked([e.target.value, ...isClicked]);
    }
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.banner}>
          <div className={styles.bannerContents}>
            <span className={styles.bannerText}>
              나에게 꼭 맞는 비대면 과외를 찾아보세요!
            </span>
            <div className={styles.searchBox}>
              <div className={styles.iconBox}>
                <MdSearch className={styles.mdSearch} />
              </div>
              <input className={styles.input} placeholder="서울대학교"></input>
            </div>
          </div>
        </div>
        <div className={styles.menuContainer}>
          <div className={styles.menu}>
            <button
              value="국어"
              className={
                isClicked.includes('국어')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              국어
            </button>
            <button
              value="수학"
              className={
                isClicked.includes('수학')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              수학
            </button>
            <button
              value="사회"
              className={
                isClicked.includes('사회')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              사회
            </button>
            <button
              value="과학"
              className={
                isClicked.includes('과학')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              과학
            </button>
            <button
              value="영어"
              className={
                isClicked.includes('영어')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              영어
            </button>
            <button
              value="자격증"
              className={
                isClicked.includes('자격증')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              자격증
            </button>
            <button
              value="기타"
              className={
                isClicked.includes('기타')
                  ? styles.activeMenu
                  : styles.defaultMenu
              }
              onClick={clickHandler}
            >
              기타
            </button>
          </div>
          <div
            className={styles.filter}
            onClick={filterHandler}
            aria-hidden="true"
          >
            <MdFilterList className={styles.mdFilterList} />
            <span>최신 순</span>
            {isOpen ? <FilterDropdown /> : null}
          </div>
        </div>
        <div className={styles.feedContainer}>
          {TutorListData.map((el) => (
            <Link to="" key={el.profileId} className={styles.list}>
              <FeedItem
                name={el.name}
                school={el.school}
                rate={el.rate}
                subjects={el.subjects}
                bio={el.bio}
                img={el.profileImage.url}
              />
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
};

export default TutorList;
