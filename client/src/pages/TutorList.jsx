import FeedItem from '../components/MainSection/FeedItem';
import styles from '../pages/TutorList.module.css';
import { MdSearch, MdFilterList } from 'react-icons/md';
import { useState } from 'react';
import FilterDropdown from '../components/MainSection/FilterDropdown';
import { Link } from 'react-router-dom';
import { ButtonTop } from '../components/Button';
import MenuButtons from '../components/MainSection/FilterButton';

const TutorListData = [
  {
    profileId: 1,
    name: '어때요',
    rate: 4.5,
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
      url: 'http://localhost:3000',
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
      url: 'http://localhost:3000',
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
  {
    profileId: 3,
    name: '마이크',
    rate: 4,
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
      url: 'http://localhost:3000',
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
      url: 'http://localhost:3000',
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
      url: 'http://localhost:3000',
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
      url: 'http://localhost:3000',
      createAt: '2023-01-10T11:07:29.0853286',
      updateAt: '2023-01-10T11:07:29.0853286',
    },
    createAt: '2023-01-10T11:07:29.0853286',
    updateAt: '2023-01-10T11:07:29.0853286',
  },
];

//TODO: 튜터 리스트를 불러오는 GET 요청 필요
//정렬 필터 파라미터 : sort = rate (기본값이 createAt 이므로 별점 순으로 변경시에만 파라미터 붙여서 요청하면 됨)
//과목 버튼 필터 파라미터 : subjectMenu 상태에서 꺼내와서 subject = 수학,영어,과학 같은 형식으로 요청
//검색창에서 검색 시 name = 강호수 와 같이 요청함

const TutorList = () => {
  //정렬 메뉴 오픈 상태
  const [isOpen, setIsOpen] = useState(false);
  //과목 필터 메뉴에서 선택한 과목들
  const [subjectMenu, setSubjectMenu] = useState([]);
  //검색창에서 입력한 검색어
  const [search, setSearch] = useState('');
  //정렬 메뉴 '최신 순'인지 '별점 순'인지
  const [filter, setFilter] = useState('');

  //정렬 메뉴 오픈 핸들러
  const filterHandler = () => {
    setIsOpen(!isOpen);
  };

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
          <div
            className={styles.filter}
            onClick={filterHandler}
            aria-hidden="true"
          >
            <MdFilterList className={styles.mdFilterList} />
            <span>{filter === 'rate' ? '평점 순' : '최신 순'}</span>
            {isOpen ? <FilterDropdown setFilter={setFilter} /> : null}
          </div>
        </div>
        <div className={styles.feedContainer}>
          {TutorListData.map((tutor) => (
            <Link
              // ! 프로필 ID 기준으로 링크 변경 필요
              to="/tutorprofile"
              key={tutor.profileId}
              className={styles.list}
            >
              <FeedItem data={tutor} userStatus="TUTOR" />
            </Link>
          ))}
        </div>
      </div>
      <ButtonTop />
    </div>
  );
};

export default TutorList;
