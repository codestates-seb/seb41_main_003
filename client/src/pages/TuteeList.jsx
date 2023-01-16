import FeedItem from '../components/MainSection/FeedItem';
import styles from '../pages/TuteeList.module.css';
import { MdSearch, MdFilterList } from 'react-icons/md';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { ButtonTop } from '../components/Button';
import MenuButtons from '../components/MainSection/FilterButton';

const TuteeListData = [
  {
    profileId: 1,
    name: '못해요',
    rate: '',
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '수학',
      },
    ],
    school: '유신고등학교',
    bio: '교복 싫어여',
    profileImage: {
      profileImageId: 1,
      url: 'http://localhost:3000',
      createAt: '2023-01-10T11:07:36.9022887',
      updateAt: '2023-01-10T11:07:36.9022887',
    },
    createAt: '2023-01-10T11:07:36.9022887',
    updateAt: '2023-01-10T11:07:36.9022887',
  },
  {
    profileId: 2,
    name: '김까치',
    rate: '',
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '영어',
      },
      {
        subjectId: 1,
        subjectTitle: '수학',
      },
    ],
    school: '강아지대학교',
    bio: '점프를 좋아하는 편입니다. 주의해주세요.',
    profileImage: {
      profileImageId: 1,
      url: 'http://localhost:3000',
      createAt: '2023-01-10T11:07:36.9022887',
      updateAt: '2023-01-10T11:07:36.9022887',
    },
    createAt: '2023-01-10T11:07:36.9022887',
    updateAt: '2023-01-10T11:07:36.9022887',
  },
  {
    profileId: 3,
    name: '김삼순',
    rate: '',
    subjects: [
      {
        subjectId: 1,
        subjectTitle: '자격증',
      },
    ],
    school: '사회인',
    bio: '파티시에가 되기 위해서 베이킹 자격증 공부를 하고 있습니다.',
    profileImage: {
      profileImageId: 1,
      url: 'http://localhost:3000',
      createAt: '2023-01-10T11:07:36.9022887',
      updateAt: '2023-01-10T11:07:36.9022887',
    },
    createAt: '2023-01-10T11:07:36.9022887',
    updateAt: '2023-01-10T11:07:36.9022887',
  },
];

//TODO: 튜티 리스트를 불러오는 GET 요청 필요
//정렬 필터 파라미터 : 튜티는 정렬 필터 없음
//과목 버튼 필터 파라미터 : subjectMenu 상태에서 꺼내와서 subject = 수학,영어,과학 같은 형식으로 요청
//검색창에서 검색 시 name = 강호수 와 같이 요청함

const TuteeList = () => {
  //과목 필터 메뉴에서 선택한 과목들
  const [subjectMenu, setSubjectMenu] = useState([]);
  //검색창에서 입력한 검색어
  const [search, setSearch] = useState('');

  const subjectHandler = (e) => {
    if (subjectMenu.includes(e.target.value)) {
      setSubjectMenu(subjectMenu.filter((el) => el !== e.target.value));
    } else {
      setSubjectMenu([e.target.value, ...subjectMenu]);
    }
  };

  const searchHandler = (e) => {
    setSearch(e.target.value);
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
              <input
                className={styles.input}
                placeholder="서울대학교"
                value={search}
                onChange={searchHandler}
              ></input>
            </div>
          </div>
        </div>
        <div className={styles.menuContainer}>
          <MenuButtons
            subjectMenu={subjectMenu}
            subjectHandler={subjectHandler}
          />
          <div className={styles.filter}>
            <MdFilterList className={styles.mdFilterList} />
            <span>최신 순</span>
          </div>
        </div>
        <div className={styles.feedContainer}>
          {TuteeListData.map((tutee) => (
            // ! 프로필 ID 기준으로 링크 변경 필요
            <Link
              to="/tuteeprofile"
              key={tutee.profileId}
              className={styles.list}
            >
              <FeedItem data={tutee} userStatus="TUTEE" />
            </Link>
          ))}
        </div>
      </div>
      <ButtonTop />
    </div>
  );
};

export default TuteeList;
