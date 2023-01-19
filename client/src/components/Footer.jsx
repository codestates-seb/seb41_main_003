import { forwardRef } from 'react';
import styles from './Footer.module.css';
const Footer = (props, ref) => {
  return (
    <footer className={styles.footer} ref={ref}>
      <div className={styles.container}>
        <div className={styles.logos}>
          <div className={styles.logo1}>
            <svg
              width="27"
              height="32"
              viewBox="0 0 27 32"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M12.1368 12.769L9.42745 13.6788V6.4005L1.98535 8.91153V6.45509L12.1368 3.02881V12.769Z"
                fill="white"
              />
              <path
                d="M21.7662 0.095412C22.5179 0.255966 23.1846 0.686456 23.6403 1.30544C24.1062 1.93137 24.3391 2.72896 24.3391 3.69819C24.3323 4.68901 24.093 5.66441 23.6403 6.54584C23.1782 7.47843 22.5421 8.31406 21.7662 9.00773C21.0232 9.68797 20.148 10.2077 19.1951 10.5344C18.3812 10.8371 17.4991 10.907 16.6477 10.7363C15.9003 10.5818 15.2381 10.1526 14.7917 9.53359C14.3344 8.90766 14.1057 8.11371 14.1057 7.15176C14.1118 6.15887 14.3466 5.18073 14.7917 4.29319C15.2466 3.3589 15.8766 2.52066 16.6477 1.82402C17.3812 1.14242 18.2488 0.621244 19.1951 0.293747C20.0166 -0.0120266 20.9075 -0.0807514 21.7662 0.095412ZM17.0061 7.51931C17.2212 7.8253 17.548 8.03443 17.9159 8.10158C18.3442 8.17549 18.7845 8.13146 19.1896 7.97421C19.6598 7.82186 20.0934 7.57407 20.4633 7.24638C20.8415 6.91633 21.1475 6.51184 21.3622 6.05819C21.5806 5.59964 21.6926 5.09767 21.6897 4.58978C21.7132 4.1549 21.5985 3.72374 21.3622 3.35793C21.2564 3.21234 21.1228 3.08914 20.9692 2.99545C20.8155 2.90176 20.6449 2.83943 20.467 2.81205C20.0366 2.75162 19.598 2.803 19.1933 2.96126C18.7256 3.11394 18.2927 3.35758 17.9196 3.67817C17.5384 3.99789 17.2284 4.39399 17.0098 4.84089C16.7884 5.29401 16.6751 5.79227 16.6786 6.29655C16.6588 6.72819 16.7732 7.15537 17.0061 7.51931Z"
                fill="white"
              />
              <path
                d="M12.1954 18.2404L8.86006 19.3667C8.86977 21.4798 10.1653 22.2992 12.7467 21.8249L11.6295 24.3906C9.51511 24.6756 8.08552 24.1528 7.34071 22.8221C6.59347 24.6562 5.16388 26.1452 3.05195 27.2892L1.92017 25.4841C3.34672 24.783 4.3487 24.0018 4.92612 23.1405C5.48311 22.3378 5.78959 21.388 5.8068 20.4111L2.54974 21.5029V19.2102L6.10521 18.0093V15.5474L8.62351 14.6976V17.1523L12.1954 15.9477V18.2404Z"
                fill="white"
              />
              <path
                d="M21.7662 11.4187C22.5176 11.5798 23.1842 12.0102 23.6403 12.6287C24.1062 13.2546 24.3391 14.0522 24.3391 15.0214C24.3323 16.0123 24.093 16.9877 23.6403 17.8691C23.1782 18.8017 22.5421 19.6373 21.7662 20.331C21.0237 21.0118 20.1483 21.5316 19.1951 21.8576C18.3812 22.1603 17.4991 22.2303 16.6477 22.0596C15.9003 21.905 15.2381 21.4759 14.7917 20.8568C14.3344 20.2321 14.1057 19.4382 14.1057 18.475C14.1118 17.4821 14.3466 16.504 14.7917 15.6164C15.2466 14.6821 15.8766 13.8439 16.6477 13.1473C17.3812 12.4657 18.2488 11.9445 19.1951 11.617C20.0166 11.3112 20.9075 11.2425 21.7662 11.4187ZM17.0061 18.8426C17.221 19.1491 17.5478 19.3589 17.9159 19.4266C18.3444 19.4999 18.7846 19.4552 19.1896 19.2975C19.6598 19.1451 20.0934 18.8973 20.4633 18.5696C20.8415 18.2396 21.1475 17.8351 21.3622 17.3814C21.5804 16.9235 21.6923 16.4221 21.6897 15.9148C21.7137 15.4799 21.599 15.0486 21.3622 14.683C21.2574 14.5355 21.1242 14.4102 20.9705 14.3147C20.8168 14.2191 20.6457 14.155 20.467 14.1262C20.0366 14.0658 19.598 14.1171 19.1933 14.2754C18.7256 14.4281 18.2927 14.6717 17.9196 14.9923C17.5388 15.3125 17.2289 15.7084 17.0098 16.155C16.7884 16.6082 16.6751 17.1064 16.6786 17.6107C16.6567 18.0454 16.7712 18.4761 17.0061 18.8426Z"
                fill="white"
              />
              <path
                d="M4.5091 31.9966L6.73263 31.236L12.0476 29.4165L18.4744 27.2148L24.0514 25.306L25.871 24.6819L26.4424 24.4854C26.5456 24.4638 26.6459 24.4296 26.7408 24.3835C26.7538 24.3797 26.7673 24.3778 26.7808 24.378C26.8554 24.3525 26.7153 23.7175 26.7808 23.6957L24.5573 24.4563L19.2423 26.2758L12.8119 28.4721L7.2403 30.3827L5.42071 31.0086L4.84754 31.2033C4.74486 31.2249 4.64524 31.2591 4.55095 31.3052C4.53892 31.3113 4.52604 31.3156 4.51274 31.3179C4.43814 31.3434 4.57643 31.9784 4.51274 32.0003L4.5091 31.9966Z"
                fill="white"
              />
              <path
                d="M0.236651 31.0829L2.46018 30.3205L7.77701 28.5009L14.2038 26.2992L19.7753 24.3904L21.5949 23.7663L22.1663 23.5698C22.269 23.5482 22.3686 23.514 22.4629 23.4679C22.4755 23.4618 22.489 23.4575 22.5029 23.4552C22.5775 23.4297 22.4374 22.7947 22.5029 22.7728L20.2794 23.5334L14.9625 25.353L8.53577 27.5547L2.96967 29.4725L1.15008 30.0985L0.578733 30.2841C0.474343 30.3041 0.372871 30.3371 0.276681 30.3823C0.263758 30.3883 0.25037 30.3931 0.236651 30.3969C0.163867 30.4205 0.302156 31.0556 0.236651 31.0792V31.0829Z"
                fill="white"
              />
            </svg>
          </div>
          <div className={styles.logo2}>
            <svg
              width="96"
              height="24"
              viewBox="0 0 96 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M0.983178 16.0206C1.91411 16.0206 2.88779 16.0079 3.90422 15.9826V9.12883H7.96517V15.852C10.4397 15.7332 12.5605 15.5614 14.3273 15.3366L14.5862 18.3883C12.544 18.7486 10.4822 18.9865 8.41164 19.1007C6.26479 19.2274 4.00633 19.2899 1.63626 19.2883H0.337225L0 16.0348C0.430635 16.0348 0.758361 16.0301 0.983178 16.0206ZM13.7312 4.57867C13.7312 6.34554 13.6925 7.8773 13.6149 9.17396C13.5276 10.641 13.3595 12.1022 13.1114 13.5508L9.05047 13.242C9.27687 11.8361 9.42331 10.5537 9.48981 9.39482C9.5563 8.2359 9.59984 6.94874 9.62042 5.53335H1.18741V2.24896H13.7312V4.57867ZM15.1989 0H19.4213V9.36157H22.2711V12.8003H19.4213V23.9216H15.1989V0Z"
                fill="white"
              />
              <path
                d="M34.1974 2.13734C35.1798 2.62006 36.014 3.35834 36.6126 4.27469C37.2047 5.19445 37.5128 6.26821 37.4984 7.36196C37.5071 8.2472 37.3095 9.12229 36.9212 9.91786C36.5329 10.7134 35.9646 11.4076 35.2614 11.9454C34.536 12.5032 33.7045 12.9073 32.8177 13.1328V15.8401C34.8521 15.7119 36.6198 15.544 38.1207 15.3366L38.4294 18.3099C36.116 18.7606 33.7745 19.0526 31.4213 19.1839C29.0464 19.3168 26.4428 19.3873 23.6105 19.3952L23.1355 16.0847C25.1715 16.0847 27.0168 16.0633 28.6712 16.0206V13.1257C27.7886 12.8985 26.9613 12.4945 26.2394 11.9383C25.5416 11.4066 24.9732 10.7238 24.577 9.94103C24.1822 9.13951 23.9843 8.25532 23.9999 7.36196C23.9866 6.26665 24.299 5.19207 24.8976 4.27469C25.5069 3.35702 26.3507 2.61906 27.3413 2.13734C28.41 1.62 29.582 1.35126 30.7694 1.35126C31.9567 1.35126 33.1287 1.62 34.1974 2.13734V2.13734ZM28.4741 8.72274C28.6943 9.09367 29.02 9.39051 29.4098 9.5753C29.8287 9.77328 30.2859 9.87704 30.7492 9.87928C31.4578 9.89991 32.1495 9.6609 32.6942 9.2072C32.9511 8.97581 33.1534 8.69023 33.2864 8.37112C33.4195 8.05201 33.48 7.70732 33.4636 7.36196C33.4636 6.54977 33.2024 5.93707 32.7013 5.51197C32.1489 5.07105 31.4552 4.84572 30.7492 4.87789C30.0592 4.84641 29.3821 5.07238 28.8493 5.51197C28.3744 5.93469 28.1226 6.54977 28.1369 7.36196C28.1214 7.838 28.2381 8.30903 28.4741 8.72274V8.72274ZM39.3342 0H43.4593V24H39.3342V0Z"
                fill="white"
              />
              <path
                d="M55.5376 10.6867C55.8989 11.8178 56.4791 12.8668 57.2451 13.774C58.0862 14.7685 59.1417 15.5593 60.3324 16.0871L58.0835 19.2171C56.9181 18.7273 55.8759 17.9852 55.0318 17.0441C54.1651 16.0716 53.4733 14.9564 52.9871 13.7479C52.4972 15.0554 51.7729 16.2626 50.8497 17.3101C49.9514 18.3172 48.8344 19.1052 47.5843 19.6137L45.3591 16.4219C46.5985 15.9139 47.6962 15.1128 48.558 14.0875C49.3621 13.1363 49.9698 12.0354 50.3463 10.8482C50.7024 9.71418 50.8896 8.53391 50.902 7.34534V7.03424H46.2924V3.74985H50.8687V0.61983H55.0342V3.74985H59.5464V7.03424H55.0342V7.34534C55.0262 8.47861 55.1961 9.60611 55.5376 10.6867V10.6867ZM60.8786 0H65.0417V8.97447H68.2738V12.4132H65.0417V23.9216H60.8786V0Z"
                fill="white"
              />
              <path
                d="M70.6868 5.67109C71.1613 4.46963 71.9636 3.42548 73.0022 2.65743C73.9753 1.95909 75.1459 1.58968 76.3436 1.60301C77.5219 1.59151 78.6724 1.96139 79.6232 2.65743C80.6472 3.43239 81.4371 4.47536 81.9054 5.67109C82.4818 7.11414 82.7628 8.65816 82.7319 10.2118C82.7649 11.784 82.4839 13.3471 81.9054 14.8094C81.4386 16.0112 80.6488 17.0607 79.6232 17.8421C78.6741 18.542 77.5228 18.9138 76.3436 18.9012C75.1426 18.9172 73.9684 18.5458 72.9951 17.8421C71.9583 17.0658 71.1589 16.0155 70.6868 14.8094C70.1035 13.3482 69.82 11.7847 69.8532 10.2118C69.8221 8.65748 70.1056 7.11297 70.6868 5.67109V5.67109ZM74.0661 13.8809C74.4984 14.7041 75.1111 15.1118 75.9043 15.1039C76.6452 15.1039 77.2286 14.6986 77.6545 13.888C78.0804 13.0774 78.2981 11.852 78.3076 10.2118C78.3076 8.60162 78.0899 7.3865 77.6545 6.5664C77.2191 5.74629 76.6357 5.34099 75.9043 5.35048C75.1277 5.35048 74.5197 5.73996 74.0804 6.5379C73.641 7.33584 73.4202 8.54463 73.4202 10.1381C73.4202 11.8116 73.6355 13.0592 74.0661 13.8809ZM89.0418 24H84.8526V0H89.0418V24Z"
                fill="white"
              />
              <path
                d="M92.1433 23.5107C91.7661 23.2901 91.452 22.976 91.2314 22.5987C91.0087 22.2161 90.893 21.7805 90.8966 21.3377C90.8934 20.901 91.0091 20.4716 91.2314 20.0957C91.4533 19.7231 91.7673 19.4139 92.1433 19.198C92.5258 18.9745 92.9614 18.858 93.4044 18.8608C93.8349 18.8614 94.2574 18.9778 94.6274 19.198C95.0073 19.4185 95.3271 19.7293 95.5583 20.1028C95.7928 20.4744 95.9156 20.9055 95.9122 21.3448C95.9153 21.7895 95.7944 22.2261 95.5631 22.6059C95.3344 22.9815 95.0141 23.2928 94.6322 23.5107C94.2602 23.7299 93.8362 23.8456 93.4044 23.8455C92.9616 23.8491 92.526 23.7334 92.1433 23.5107V23.5107Z"
                fill="white"
              />
            </svg>
          </div>
          <div className={styles.logo3}>
            <svg
              width="53"
              height="32"
              viewBox="0 0 53 32"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M0.095459 0H12.5546V3.0736H8.20163V14.4567H4.44844V3.0736H0.095459V0Z"
                fill="white"
              />
              <path
                d="M19.6722 3.60388H23.446V14.4567H19.873V12.3974H19.752C19.5339 13.0607 19.0974 13.6305 18.5138 14.014C17.8887 14.4115 17.159 14.6132 16.4184 14.5932C15.7293 14.6083 15.0497 14.43 14.4569 14.0783C13.8853 13.7336 13.4244 13.2325 13.1286 12.6342C12.8063 11.978 12.6456 11.2543 12.6601 10.5233V3.60388H16.439V9.74336C16.4115 10.2147 16.5611 10.6793 16.8586 11.0459C17.0076 11.2068 17.1903 11.3327 17.3937 11.4146C17.5971 11.4965 17.8161 11.5323 18.035 11.5196C18.2571 11.5297 18.4788 11.4922 18.6853 11.4096C18.8917 11.327 19.0781 11.2013 19.232 11.0408C19.389 10.8611 19.5087 10.6521 19.5843 10.4259C19.6598 10.1997 19.6897 9.96066 19.6722 9.72277V3.60388Z"
                fill="white"
              />
              <path
                d="M31.8509 6.35054H29.9562V10.9841C29.9455 11.089 29.9592 11.195 29.9962 11.2937C30.0333 11.3924 30.0927 11.4811 30.1699 11.553C30.3734 11.6832 30.6139 11.7429 30.8546 11.7229C31.1121 11.7229 31.4982 11.7023 31.9924 11.6637V14.4747C31.3974 14.577 30.7942 14.6244 30.1905 14.6163C28.8519 14.6163 27.8454 14.3495 27.171 13.8157C26.4965 13.282 26.1653 12.4634 26.1773 11.3599V6.35054H24.7847V3.60387H26.1825V1.02966H29.9562V3.60387H31.8509V6.35054Z"
                fill="white"
              />
              <path
                d="M35.5497 13.9574C34.7248 13.5091 34.0512 12.8266 33.6138 11.9959C33.1523 11.0889 32.924 10.0812 32.9497 9.06384C32.9274 8.04646 33.1592 7.0396 33.6241 6.13439C34.0615 5.30368 34.7351 4.62112 35.5599 4.17285C36.4862 3.71389 37.5059 3.4751 38.5396 3.4751C39.5733 3.4751 40.593 3.71389 41.5192 4.17285C42.343 4.61996 43.0144 5.30302 43.4473 6.13439C43.9052 7.04176 44.1333 8.0477 44.1115 9.06384C44.1335 10.0808 43.9054 11.0876 43.4473 11.9959C43.0144 12.8272 42.343 13.5103 41.5192 13.9574C40.593 14.4164 39.5733 14.6551 38.5396 14.6551C37.5059 14.6551 36.4862 14.4164 35.5599 13.9574H35.5497ZM37.2615 11.0975C37.3855 11.331 37.5709 11.5264 37.7977 11.6625C38.0245 11.7985 38.2841 11.8702 38.5486 11.8697C38.8044 11.8683 39.0549 11.7974 39.2735 11.6645C39.492 11.5316 39.6704 11.3419 39.7894 11.1155C40.1232 10.4962 40.2808 9.79731 40.245 9.09473C40.2818 8.36879 40.1264 7.646 39.7945 6.99932C39.6803 6.76759 39.5025 6.57314 39.2818 6.43868C39.0612 6.30421 38.8069 6.23528 38.5486 6.23993C38.2861 6.23212 38.0267 6.29758 37.7993 6.42899C37.572 6.56039 37.3857 6.75254 37.2615 6.98388C36.9233 7.62048 36.7616 8.33595 36.793 9.05611C36.7653 9.76607 36.927 10.4706 37.2615 11.0975Z"
                fill="white"
              />
              <path
                d="M45.7383 3.60373H49.3911V5.6631H49.5121C49.6464 5.03814 49.9679 4.46889 50.4336 4.03105C50.8574 3.66859 51.4 3.4752 51.9576 3.48789C52.2973 3.48992 52.6357 3.52963 52.9666 3.60631V6.84723C52.747 6.77773 52.5211 6.72945 52.2922 6.70307C52.0456 6.66914 51.7971 6.65108 51.5483 6.64902C51.1806 6.63927 50.8171 6.72816 50.4954 6.90644C50.189 7.07899 49.9381 7.33525 49.7721 7.64523C49.5938 7.98085 49.5051 8.35679 49.5146 8.7367V14.4566H45.7383V3.60373Z"
                fill="white"
              />
              <path
                d="M0 31.7812V17.327H5.44959C6.78291 17.2943 8.10375 17.5905 9.29545 18.1894C10.3631 18.75 11.2362 19.6204 11.8002 20.6864C12.4092 21.8805 12.7109 23.2076 12.678 24.5477C12.7129 25.8873 12.413 27.2145 11.8053 28.409C11.2436 29.4739 10.3732 30.3442 9.30832 30.906C8.12758 31.5076 6.81558 31.8058 5.49078 31.7735L0 31.7812ZM5.35177 28.548C6.01378 28.57 6.67216 28.4415 7.27728 28.1722C7.80332 27.9075 8.21772 27.4639 8.44597 26.9211C8.74159 26.1687 8.87747 25.3631 8.84497 24.5554C8.87951 23.7437 8.74177 22.934 8.44082 22.1794C8.20852 21.633 7.78597 21.1895 7.25153 20.9309C6.61999 20.6624 5.93719 20.5359 5.25138 20.5602H3.79438V28.5403L5.35177 28.548Z"
                fill="white"
              />
              <path
                d="M15.5095 19.533C15.229 19.3889 14.9925 19.172 14.8248 18.9049C14.6563 18.6422 14.567 18.3366 14.5674 18.0245C14.5677 17.7166 14.6571 17.4153 14.8248 17.157C14.993 16.889 15.2293 16.6705 15.5095 16.5238C15.7993 16.37 16.1224 16.2897 16.4504 16.2897C16.7785 16.2897 17.1015 16.37 17.3913 16.5238C17.6693 16.6727 17.9043 16.8908 18.0735 17.157C18.242 17.4149 18.3314 17.7164 18.3309 18.0245C18.3322 18.3367 18.2427 18.6426 18.0735 18.9049C17.9047 19.1702 17.6696 19.3867 17.3913 19.533C17.1002 19.6825 16.7777 19.7605 16.4504 19.7605C16.1232 19.7605 15.8006 19.6825 15.5095 19.533ZM14.5751 20.9411H18.3489V31.7811H14.5751V20.9411Z"
                fill="white"
              />
              <path
                d="M26.7537 23.6826H24.7587V31.7887H20.985V23.6826H19.5872V20.941H20.985V20.5601C20.985 19.2747 21.3282 18.3016 22.0146 17.6409C22.7011 16.9802 23.645 16.649 24.8463 16.6473C25.5497 16.6432 26.2518 16.7105 26.9417 16.8481V19.5819L26.3342 19.5381L25.9995 19.5227C25.6737 19.5093 25.352 19.5994 25.0805 19.7801C24.9702 19.8745 24.8838 19.9937 24.8284 20.1279C24.7729 20.2621 24.7499 20.4076 24.7613 20.5523V20.9333H26.7563L26.7537 23.6826Z"
                fill="white"
              />
              <path
                d="M34.6823 23.6826H32.6925V31.7887H28.911V23.6826H27.5132V20.941H28.911V20.5601C28.911 19.2747 29.2542 18.3016 29.9407 17.6409C30.6271 16.9802 31.571 16.649 32.7723 16.6473C33.4757 16.6432 34.1778 16.7105 34.8677 16.8481V19.5819L34.2602 19.5381L33.9307 19.5227C33.6049 19.5093 33.2832 19.5994 33.0117 19.7801C32.9014 19.8745 32.815 19.9937 32.7595 20.1279C32.7041 20.2621 32.6811 20.4076 32.6925 20.5523V20.9333H34.6901L34.6823 23.6826Z"
                fill="white"
              />
              <path
                d="M36.1728 31.7424C35.8793 31.5715 35.636 31.3265 35.467 31.032C35.298 30.7374 35.2094 30.4036 35.21 30.064C35.2075 29.7274 35.2965 29.3964 35.4674 29.1064C35.6368 28.8165 35.8791 28.576 36.1702 28.4088C36.465 28.2359 36.8015 28.1469 37.1432 28.1514C37.4758 28.1493 37.8025 28.2383 38.088 28.4088C38.3811 28.5787 38.6278 28.8184 38.8062 29.1064C38.9873 29.3929 39.0829 29.7251 39.0816 30.064C39.0843 30.4073 38.9906 30.7444 38.8113 31.0371C38.6365 31.33 38.3892 31.5729 38.0931 31.7424C37.8051 31.9114 37.4772 32.0003 37.1432 31.9998C36.8025 32.0026 36.4673 31.9137 36.1728 31.7424Z"
                fill="white"
              />
            </svg>
          </div>
        </div>
        <div className={styles.copyright}>
          <div>Copyright ⓒ 2023</div>
          <div>Ssuayngnim</div>
          <div>All rights reserved.</div>
        </div>
      </div>
    </footer>
  );
};

export default forwardRef(Footer);
